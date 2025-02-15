package org.eclipse.dataspaceconnector.dataplane.spi.pipeline;

import org.eclipse.dataspaceconnector.dataplane.spi.result.TransferResult;
import org.eclipse.dataspaceconnector.spi.result.Result;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.DataFlowRequest;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * A sink factory whose sole purpose is to validate {@link DataFlowRequest} whose destination address type is OutputStream.
 * This is required for synchronous data transfers in which an {@link OutputStreamDataSink} is used to convey the data to the call response.
 */
public class OutputStreamDataSinkFactory implements DataSinkFactory {

    public static final String TYPE = "OutputStream";

    @Override
    public boolean canHandle(DataFlowRequest request) {
        return TYPE.equals(request.getDestinationDataAddress().getType());
    }

    @Override
    public @NotNull Result<Boolean> validate(DataFlowRequest request) {
        return canHandle(request) ? Result.success(true) :
                Result.failure(String.format("%s: Cannot handle destination data address with type: %s",
                        getClass().getSimpleName(), request.getDestinationDataAddress().getType()));
    }

    @Override
    public DataSink createSink(DataFlowRequest request) {
        return source -> CompletableFuture.completedFuture(TransferResult.success());
    }
}
