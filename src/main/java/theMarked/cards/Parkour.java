package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class Parkour extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Parkour.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_Parkour.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public int specialDamage;

    // /STAT DECLARATION/

    public Parkour() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void applyPowers() {
        int olddamage = baseDamage;
        if (AbstractDungeon.player.hasPower("Dexterity"))
        {
            this.baseDamage += AbstractDungeon.player.getPower("Dexterity").amount * magicNumber;
        }
        super.applyPowers();
        this.baseDamage = olddamage;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int olddamage = baseDamage;
        if (AbstractDungeon.player.hasPower("Dexterity"))
        {
            this.baseDamage += AbstractDungeon.player.getPower("Dexterity").amount * magicNumber;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = olddamage;
        isDamageModified = baseDamage != damage;

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}