package mobi.thalic.bakingapp;

import mobi.thalic.bakingapp.data.BakingStep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BakingStepBaseTests {
    private static final int STEP_KEY_VALUE = 669;
    private static final int RECIPE_KEY_VALUE = 21;
    private static final int ID_VALUE = 55;
    private static final String SHORT_DESCRIPTION_VALUE = "Experimental short description";
    private static final String DESCRIPTION_VALUE = "Sufficient description";
    private static final String VIDEO_URL_VALUE = "https://example.com/video/464848648.png";
    private static final String THUMBNAIL_URL_VALUE = "https://example.com/thumbnails/4848664.png";

    // Test constructor and getters
    BakingStep bakingStep = new BakingStep(STEP_KEY_VALUE, RECIPE_KEY_VALUE, ID_VALUE,
            SHORT_DESCRIPTION_VALUE, DESCRIPTION_VALUE, VIDEO_URL_VALUE, THUMBNAIL_URL_VALUE);

    @Test
    public void testBakingStepStepKeyGetter() {
        assertEquals(STEP_KEY_VALUE, bakingStep.getStepKey());
    }

    @Test
    public void testBakingStepRecipeKeyGetter() {
        assertEquals(RECIPE_KEY_VALUE, bakingStep.getRecipeKey());
    }

    @Test
    public void testBakingStepIdGetter() {
        assertEquals(ID_VALUE, bakingStep.getId());
    }

    @Test
    public void testBakingStepShortDescriptionGetter() {
        assertEquals(SHORT_DESCRIPTION_VALUE, bakingStep.getShortDescription());
    }

    @Test
    public void testBakingStepDescriptionGetter() {
        assertEquals(DESCRIPTION_VALUE, bakingStep.getDescription());
    }

    @Test
    public void testBakingStepVideoUrlGetter() {
        assertEquals(VIDEO_URL_VALUE, bakingStep.getVideoURL());
    }

    @Test
    public void testBakingStepThumbnailUrlGetter() {
        assertEquals(THUMBNAIL_URL_VALUE, bakingStep.getThumbnailURL());
    }

    // Test default constructor and setters
    BakingStep bakingStepDefault = new BakingStep();

    @Test
    public void testBakingStepStepKeySetter() {
        int temp = 888;
        bakingStepDefault.setStepKey(temp);
        assertEquals(temp, bakingStepDefault.getStepKey());
    }

    @Test
    public void testBakingStepRecipeKeySetter() {
        int temp = 999;
        bakingStepDefault.setRecipeKey(temp);
        assertEquals(temp, bakingStepDefault.getRecipeKey());
    }

    @Test
    public void testBakingStepIdSetter() {
        int temp = 222;
        bakingStepDefault.setId(temp);
        assertEquals(temp, bakingStepDefault.getId());
    }

    @Test
    public void testBakingStepShortDescriptionSetter() {
        String temp = "A very short description";
        bakingStepDefault.setShortDescription(temp);
        assertEquals(temp, bakingStepDefault.getShortDescription());
    }

    @Test
    public void testBakingStepDescriptionSetter() {
        String temp = "a not so short description";
        bakingStepDefault.setDescription(temp);
        assertEquals(temp, bakingStepDefault.getDescription());
    }

    @Test
    public void testBakingStepVideoURLSetter() {
        String temp = "https://somewhere.com/videos/546546546.png";
        bakingStepDefault.setVideoURL(temp);
        assertEquals(temp, bakingStepDefault.getVideoURL());
    }

    @Test
    public void testBakingStepThumbnailUrlSetter() {
        String temp = "https://somewhere.com/thumbnails/46846848468.png";
        bakingStepDefault.setThumbnailURL(temp);
        assertEquals(temp, bakingStepDefault.getThumbnailURL());
    }
}
