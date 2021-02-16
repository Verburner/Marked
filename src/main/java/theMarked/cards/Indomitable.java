package theMarked.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class Indomitable extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Indomitable.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_Indomitable.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 2;


    // /STAT DECLARATION/


    public Indomitable() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var1 = p.powers.iterator();
        int energyToGain = 0;

        while(var1.hasNext()) {
            AbstractPower pow = (AbstractPower)var1.next();
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                energyToGain++;
                this.addToBot(new RemoveSpecificPowerAction(p, p, pow.ID));
            }
        }

        if (energyToGain > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyToGain));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
