package engine.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SoundEffectPool {
    private String file;
    private List<SoundEffect> soundEffects = new ArrayList<>();

    public SoundEffectPool(String file) {
        this.file = file;

        newSoundEffect();
    }

    private SoundEffect newSoundEffect() {
        SoundEffect sfx = new SoundEffect(this.file);
        soundEffects.add(sfx);
        return sfx;
    }

    public SoundEffect get() {
        List<SoundEffect> available = soundEffects.stream().filter(x -> !x.isPlaying()).collect(Collectors.toList());
        if (available.size() > 0) {
            return available.get(0);
        } else {
            return newSoundEffect();
        }
    }
}
