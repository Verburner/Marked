package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.cards.AlyssasBlade;
import theMarked.characters.TheMarked;
import theMarked.powers.ChargePower;

import java.util.Iterator;

public class ReabsorbAction extends AbstractGameAction{
    private AbstractCard card;

    public ReabsorbAction() { }

    public void update() {

        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        int chargeToGain = 0;
        AbstractPlayer p = AbstractDungeon.player;

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                chargeToGain = c.baseDamage;
                chargeToGain += c.baseMagicNumber*3;
                chargeToGain += ((AlyssasBlade) c).defaultBaseSecondMagicNumber*3;
                chargeToGain += c.baseBlock*3;
                this.addToBot(new ExhaustSpecificCardAction(c,p.hand));
            }
        }
        this.addToBot(new ApplyPowerAction(p,p,new ChargePower(p,p,chargeToGain),chargeToGain));
        this.addToBot(new ResetAnimationAction());

        this.isDone = true;
    }
}
