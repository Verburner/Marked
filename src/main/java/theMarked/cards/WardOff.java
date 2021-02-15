package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.actions.BanishBlockAction;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;
import theMarked.powers.BanishPower;

import static theMarked.DefaultMod.makeCardPath;

public class WardOff extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(WardOff.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_WardOff.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int UPGRADE_PLUS_MAGIC = 0;


    // /STAT DECLARATION/


    public WardOff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = baseMagicNumber;
        this.baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CastAnimationAction());
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BanishPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new BanishBlockAction(p,m));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
