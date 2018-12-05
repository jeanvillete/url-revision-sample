import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestIt {

    @Test
    public void testCSS() {
        String cssResource = "http://myhost.com:portnumber/url-revision-sample/static/v/201811231949/css/my-custom-style.css";
        String cssPattern = "^.+\\/static\\/v\\/\\d{12}\\/css\\/(.*)$";
        String changeTo = "\\/static\\/compressed\\/css\\/$1";

        String _return = Pattern.compile( cssPattern ).matcher( cssResource ).matches() ? cssResource.replaceAll( cssPattern, changeTo ) : null;

        Assert.assertEquals( _return, "/static/compressed/css/my-custom-style.css" );
        System.out.println( _return );
    }

    @Test
    public void testJS() {
        String jsResource = "http://myhost.com:portnumber/url-revision-sample/static/v/201811231949/js/my-custom-behaviour.js";
        String jsPattern = "^.+\\/static\\/v\\/\\d{12}\\/js\\/(.*)$";
        String changeTo = "\\/static\\/compressed\\/js\\/$1";

        String _return = Pattern.compile( jsPattern ).matcher( jsResource ).matches() ? jsResource.replaceAll( jsPattern, changeTo ) : null;

        Assert.assertEquals( _return, "/static/compressed/js/my-custom-behaviour.js" );
        System.out.println( _return );
    }

}
