package theMarked.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import static theMarked.DefaultMod.makeRelicOutlinePath;
import static theMarked.DefaultMod.makeRelicPath;

public class
Gun extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 3 Charge.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Gun");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Gun.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Gun.png"));

    public Gun() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
