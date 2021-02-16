package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.cards.AlyssasBlade;

import java.util.Iterator;

public class SwordWeakAction extends AbstractGameAction{
    private AbstractCard card;

    public SwordWeakAction(int amount) {
        this.amount = amount;
    }

    public void update() {

        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseMagicNumber += this.amount;
                c.applyPowers();
                c.initializeDescription();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseMagicNumber += this.amount;
                c.applyPowers();
                c.initializeDescription();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseMagicNumber += this.amount;
                c.applyPowers();
                c.initializeDescription();
            }
        }

        var1 = AbstractDungeon.player.limbo.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseMagicNumber += this.amount;
                c.applyPowers();
                c.initializeDescription();
            }
        }

        if (AbstractDungeon.player.cardInUse instanceof  AlyssasBlade)
        {
            c = AbstractDungeon.player.cardInUse;
            c.baseMagicNumber += this.amount;
            c.applyPowers();
            c.initializeDescription();
        }

        this.isDone = true;
    }
}
