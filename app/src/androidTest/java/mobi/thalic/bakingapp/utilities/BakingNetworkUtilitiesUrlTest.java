package mobi.thalic.bakingapp.utilities;

import android.net.Uri;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import mobi.thalic.bakingapp.utilities.BakingNetworkUtilities;

import static org.junit.Assert.assertEquals;

public class BakingNetworkUtilitiesUrlTest {
    private static final String BAKING_RECIPES_HTML =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Test
    public void testBakingNetworkUtilitiesBuildURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(BAKING_RECIPES_HTML).buildUpon().build();
        URL url = new URL(builtUri.toString());

        assertEquals(url, BakingNetworkUtilities.buildUrl());
    }
}
