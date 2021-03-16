package theMarked.cards;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theMarked.DefaultMod;
import theMarked.VFX.ImprovisedWeaponEffect;

import java.util.concurrent.ThreadLocalRandom;

import static theMarked.DefaultMod.makeCardPath;

//@AutoAdd.Ignore
public class ImprovisedWeapon extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ImprovisedWeapon.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_ImprovisedWeapon.png");
    public static final String IMG_beta = makeCardPath("Attack_ImprovisedWeapon_beta.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESC_0 = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String DESC_card1 = cardStrings.EXTENDED_DESCRIPTION[1];
    public static final String DESC_card2 = cardStrings.EXTENDED_DESCRIPTION[2];
    public static final String DESC_card3 = cardStrings.EXTENDED_DESCRIPTION[3];
    public static final String DESC_ene1 = cardStrings.EXTENDED_DESCRIPTION[4];
    public static final String DESC_ene2 = cardStrings.EXTENDED_DESCRIPTION[5];
    public static final String DESC_apply = cardStrings.EXTENDED_DESCRIPTION[6];
    public static final String DESC_vuln = cardStrings.EXTENDED_DESCRIPTION[7];
    public static final String DESC_weak = cardStrings.EXTENDED_DESCRIPTION[8];
    public static final String DESC_end = cardStrings.EXTENDED_DESCRIPTION[9];


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    public int effect ;

    public int specialDamage;

    // /STAT DECLARATION/

    public ImprovisedWeapon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        baseDamage = DAMAGE;
        damage = baseDamage;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;

        if(AbstractDungeon.player != null && AbstractDungeon.cardRng != null){
            effect = AbstractDungeon.cardRng.random(1,4);
        }else {
            effect = 0;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ImprovisedWeaponEffect(m.hb.cX, m.hb.cY)));
        }
        this.addToBot(new WaitAction(0.4F));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (effect == 1) this.addToBot(new DrawCardAction(p, magicNumber, false));
        if (effect == 2) this.addToBot(new GainEnergyAction(magicNumber));
        if (effect == 3) this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
        if (effect == 4) this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
    }

    @Override
    public void initializeDescription() {
        String baseRawDescription = rawDescription;
        if (effect == 0 || AbstractDungeon.player == null)
            rawDescription += DESC_0 + DESC_end;
        else {
            if (effect == 1) {
                if (upgraded) rawDescription += DESC_card1 + magicNumber + DESC_card3 + DESC_end;
                else rawDescription += DESC_card1 + magicNumber + DESC_card2 + DESC_end;
            }
            if (effect == 2) {
                if (upgraded) rawDescription += DESC_ene2 + DESC_end;
                else rawDescription += DESC_ene1 + DESC_end;
            }
            //if (effect == 3) rawDescription += DESC_gain + DESC_charge+ DESC_end;
            if (effect == 3) rawDescription += DESC_apply + magicNumber + DESC_weak + DESC_end;
            if (effect == 4) rawDescription += DESC_apply + magicNumber + DESC_vuln + DESC_end;

            //if (effect == 6) rawDescription += DESC_apply + DESC_banish + DESC_end;
        }

        super.initializeDescription();
        rawDescription = baseRawDescription;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}