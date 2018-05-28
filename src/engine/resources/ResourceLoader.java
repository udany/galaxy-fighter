package engine.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

public class ResourceLoader {
    private ResourceLoader() {}

    private static ResourceLoader instance;
    private static ResourceLoader getInstance() {
        if (instance == null) instance = new ResourceLoader();

        return instance;
    }

    public static URL getResourceUrl(String file) {
        return getInstance().getClass().getResource(file);
    }

    private static HashMap<String, BufferedImage> imageCache = new HashMap<>();

    public static BufferedImage loadImage(String file) {
        if (!imageCache.containsKey(file)) {
            URL url = getResourceUrl(file);

            try{
                BufferedImage img = ImageIO.read(url);
                imageCache.put(file, img);
                return img;
            }catch (Exception e){
                System.out.println("Failed loading image "+url);
                return null;
            }
        } else {
            return imageCache.get(file);
        }
    }
}