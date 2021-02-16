package theMarked.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import static theMarked.DefaultMod.makeRelicOutlinePath;
import static theMarked.DefaultMod.makeRelicPath;

public class BronzeBlade extends CustomRelic {
    private int amount = 10;
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 3 Charge.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BronzeBlade");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeBlade.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeBlade.png"));

    public BronzeBlade() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, amount)));
        flash();
    }

    @Override
    public void obtain() {
        // Replace the starter relic, or just give the relic if starter isn't found
        if (AbstractDungeon.player.hasRelic("theMarked:ArtifactOfAir")) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals("theMarked:ArtifactOfAir")) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("theMarked:ArtifactOfAir");
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
