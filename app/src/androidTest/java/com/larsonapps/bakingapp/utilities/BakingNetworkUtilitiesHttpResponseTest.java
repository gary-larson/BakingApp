package com.larsonapps.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BakingNetworkUtilitiesHttpResponseTest {
    private static final String BAKING_RECIPES_HTML =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Test
    public void testBakingNetworkUtilitiesGetResponseFromHTTPUrl() throws IOException {
        Uri builtUri = Uri.parse(BAKING_RECIPES_HTML).buildUpon().build();
        URL url = new URL(builtUri.toString());

        assertNotNull(BakingNetworkUtilities.getResponseFromHttpUrl(url));
    }
}
