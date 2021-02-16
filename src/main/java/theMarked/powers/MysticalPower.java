package theMarked.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import theMarked.DefaultMod;
import theMarked.actions.BanishChargeAction;
import theMarked.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;

import static theMarked.DefaultMod.makePowerPath;

//Consume and deal damage equal to amount on attack.

public class MysticalPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MysticalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int playedThisTurn;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("MysticalPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("MysticalPower32.png"));

    public MysticalPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.playedThisTurn = 0;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            playedThisTurn++;
            if (playedThisTurn>=3)
            {
                this.flash();

                Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

                while(var3.hasNext()) {
                    AbstractMonster mo = (AbstractMonster)var3.next();
                    if (!mo.isDead) {
                        this.addToBot(new VFXAction(owner, new BorderLongFlashEffect(Color.PURPLE), 0.0F, true));
                        this.addToBot(new ApplyPowerAction(mo, owner, new BanishPower(mo, owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
                        this.addToBot(new BanishChargeAction(this.owner,mo));
                    }
                }
                playedThisTurn = 0;
            }
        }
    }
    @Override
    public void atStartOfTurn() {
        playedThisTurn = 0;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MysticalPower(owner, source, amount);
    }
}
