package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class Pokerface extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Pokerface.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_WastefulDefense.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 0;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/


    public Pokerface() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.block = baseBlock;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        boolean twice = false;
        Iterator var1 = p.powers.iterator();
        while(var1.hasNext()) {
            AbstractPower pow = (AbstractPower)var1.next();
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                twice = true;
            }
        }
        if (twice) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
