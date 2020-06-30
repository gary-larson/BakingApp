package com.larsonapps.bakingapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class to build url's and retrieving data from the internet
 */
public class BakingNetworkUtilities {
    private static final String BAKING_RECIPES_HTML =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildUrl() {

        // Build Uri
        Uri builtUri = Uri.parse(BAKING_RECIPES_HTML).buildUpon().build();
        URL url = null;
        // Convert to URL
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // return url
        return url;
    }

    /**
     * Method to get the response from the built URL
     * @param url to submit
     * @return the response
     * @throws IOException in case of error
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        // Declare variable for the connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            // Create an input stream
            InputStream in = urlConnection.getInputStream();

            // Use scanner to get response
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            // Test for response
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                // get response
                return scanner.next();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // in case of error return null
            return null;
        } finally {
            // disconnect stream
            urlConnection.disconnect();
        }
    }
}
