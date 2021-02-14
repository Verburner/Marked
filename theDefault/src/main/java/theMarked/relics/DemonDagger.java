package theMarked.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theMarked.DefaultMod;
import theMarked.actions.BanishLoseHpAction;
import theMarked.powers.BanishPower;
import theMarked.util.TextureLoader;

import static theMarked.DefaultMod.makeRelicOutlinePath;
import static theMarked.DefaultMod.makeRelicPath;


public class DemonDagger extends CustomRelic implements OnApplyPowerRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 3 Charge.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("DemonDagger");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DemonDagger.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DemonDagger.png"));

    public boolean triggered;

    public DemonDagger() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
        triggered = false;
    }

    @Override
    public void atTurnStart() {
        triggered = false;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public boolean onApplyPower(AbstractPower pow, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (pow instanceof BanishPower && !triggered)
        {
            triggered = true;
            this.flash();
        }
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower pow, AbstractCreature target, AbstractCreature source, int amount) {
        if (pow instanceof BanishPower && !triggered)
        {
            triggered = true;
            amount += 2;
            this.flash();
        }
        return amount;
    }
}
