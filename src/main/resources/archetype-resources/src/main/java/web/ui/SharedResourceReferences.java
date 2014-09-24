#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui;

import java.net.URL;

import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.util.time.Duration;

/**
 * @author jabaraster
 */
public final class SharedResourceReferences {

    private static final String RESOURCE_NAME_BACK_IMAGE = "back";   //$NON-NLS-1$
    private static final String RESOURCE_NAME_FAVICON    = "favicon"; //$NON-NLS-1$

    static {
        initialize();
    }

    private SharedResourceReferences() {
        // 処理なし
    }

    /**
     * @return -
     */
    public static ResourceReference getBackImage() {
        return new SharedResourceReference(RESOURCE_NAME_BACK_IMAGE);
    }

    /**
     * @return -
     */
    public static ResourceReference getFavicon() {
        return new SharedResourceReference(RESOURCE_NAME_BACK_IMAGE);
    }

    private static void initialize() {
        final WebApplication application = WebApplication.get();
        mountResource(application, RESOURCE_NAME_BACK_IMAGE, "brickwall.png", Duration.days(10)); //$NON-NLS-1$ 
        mountResource(application, RESOURCE_NAME_FAVICON, "favicon.png", Duration.days(10)); //$NON-NLS-1$
    }

    private static void mountResource( //
            final WebApplication pApplication //
            , final String pResourceName //
            , final String pFilePath //
            , final Duration pCacheDuration) {
        pApplication.mountResource(pResourceName, new ResourceReference(pResourceName) {
            private static final long serialVersionUID = -8982729375012083247L;

            @SuppressWarnings("resource")
            @Override
            public IResource getResource() {
                final URL resourceUrl = SharedResourceReferences.class.getResource(pFilePath);
                final ResourceStreamResource ret = new ResourceStreamResource(new UrlResourceStream(resourceUrl));
                ret.setCacheDuration(pCacheDuration);
                return ret;
            }
        });
    }
}
