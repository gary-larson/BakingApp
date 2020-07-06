package com.larsonapps.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;

import com.larsonapps.bakingapp.R;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

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
