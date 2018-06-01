package engine.resources;

import engine.sound.SoundData;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.function.Supplier;

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

    interface cacheLoader<T> {
        public T load(String file) throws Exception;
    }

    private static <T> T loadWithCache(HashMap<String, T> cache, String key, cacheLoader<T> loader) {
        if (!cache.containsKey(key)) {
            try {
                T resource = loader.load(key);

                cache.put(key, resource);
            } catch (Exception e) {
                System.out.println("Failed loading resource " + key);
                return null;
            }
        }

        return cache.get(key);
    }



    private static HashMap<String, BufferedImage> imageCache = new HashMap<>();
    public static BufferedImage loadImage(String file) {
        return loadWithCache(imageCache, file, fileName -> {
            URL url = getResourceUrl(fileName);
            return ImageIO.read(url);
        });
    }

    private static HashMap<String, SoundData> soundCache = new HashMap<>();
    public static SoundData loadSound(String file) {
        return loadWithCache(soundCache, file, fileName -> {
            return new SoundData(file);
        });
    }
}