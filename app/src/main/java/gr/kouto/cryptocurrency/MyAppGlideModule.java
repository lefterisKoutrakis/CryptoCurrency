package gr.kouto.cryptocurrency;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.engine.executor.GlideExecutor.newDiskCacheExecutor;
import static com.bumptech.glide.load.engine.executor.GlideExecutor.newSourceExecutor;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        final GlideExecutor.UncaughtThrowableStrategy myUncaughtThrowableStrategy = new GlideExecutor.UncaughtThrowableStrategy() {
            @Override
            public void handle(Throwable t) {
                System.out.println("Exception raised with load image by Glide! " + t.getMessage());
                t.printStackTrace();
            }
        };

        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig());

        builder.setDiskCacheExecutor(newDiskCacheExecutor(myUncaughtThrowableStrategy));
        builder.setResizeExecutor(newSourceExecutor(myUncaughtThrowableStrategy));
    }
}
