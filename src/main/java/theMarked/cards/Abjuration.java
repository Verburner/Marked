package theMarked.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;
import theMarked.powers.BanishPower;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class Abjuration extends AbstractMarkedCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Abjuration.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_Abjuration.png");
    public static final String IMG_beta = makeCardPath("Attack_Abjuration_beta.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int MAGIC_NUMBER = 3;

    public int specialDamage;

    // /STAT DECLARATION/

    public Abjuration() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        DefaultMod.loadJokeCardImage(this,IMG_beta);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC_NUMBER;
        magicNumber = MAGIC_NUMBER;
    }

    public void loadBetaImage(String img) {
        Texture cardTexture;
        if (imgMap.containsKey(img)) {
            cardTexture = (Texture)imgMap.get(img);
        } else {
            cardTexture = ImageMaster.loadImage(img);
            imgMap.put(img, cardTexture);
        }

        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "jokePortrait", cardImg);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new CastAnimationAction());
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new ApplyPowerAction(m, p, new BanishPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        if (m.hasPower(MinionPower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(m, p, new BanishPower(m, p, 10 - this.magicNumber), 10 - this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var3.next();
                if (!mo.isDead) {
                    if (this.upgraded && mo.hasPower(MinionPower.POWER_ID) && mo != m) {
                        this.calculateCardDamage(mo);
                        this.addToBot(new DamageAction(mo, new DamageInfo(p, damage, damageTypeForTurn),
                                AbstractGameAction.AttackEffect.FIRE));
                        this.addToBot(new ApplyPowerAction(m, p, new BanishPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                        this.addToBot(new ApplyPowerAction(mo, p, new BanishPower(mo, p, 10 - this.magicNumber), 10 - this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
            }
        }


    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}