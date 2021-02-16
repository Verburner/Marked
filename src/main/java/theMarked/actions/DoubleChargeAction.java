package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theMarked.powers.ChargePower;

import java.util.Iterator;

public class DoubleChargeAction extends AbstractGameAction{
    private AbstractPlayer p;

    public DoubleChargeAction(AbstractPlayer p) {
        this.p = p;
    }

    public void update() {
        if (p.hasPower("theMarked:Charge"))
        {
            AbstractPower pow = p.getPower("theMarked:Charge");
            this.addToBot(new ApplyPowerAction(p, p, new ChargePower(p,p,pow.amount),pow.amount));
        }

        this.isDone = true;
    }
}
