package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class DeterminationPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("DeterminationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int times = 1;
    public int applied = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DeterminationPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DeterminationPower32.png"));

    public DeterminationPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.times = 1;
        this.priority = 1;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void atStartOfTurn()
    {
        applied = 0;
    }

    @Override
    public void updateDescription() {
        if (times > 1) description = DESCRIPTIONS[1]+times+DESCRIPTIONS[2]+amount+DESCRIPTIONS[3];
        else description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeterminationPower(owner, source, amount);
    }
}
