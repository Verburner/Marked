package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.cards.AlyssasBlade;
import theMarked.characters.TheMarked;

import java.util.Iterator;

public class SwordBuffAction extends AbstractGameAction{
    private AbstractCard card;

    public SwordBuffAction(int amount) {
        this.amount = amount;
    }

    public void update() {

        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseDamage += this.amount;
                if (c.baseDamage < 0) c.baseDamage = 0;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseDamage += this.amount;
                if (c.baseDamage < 0) c.baseDamage = 0;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseDamage += this.amount;
                if (c.baseDamage < 0) c.baseDamage = 0;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.limbo.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof AlyssasBlade) {
                c.baseDamage += this.amount;
                if (c.baseDamage < 0) c.baseDamage = 0;
                c.applyPowers();
            }
        }

        if (AbstractDungeon.player.cardInUse instanceof  AlyssasBlade)
        {
            c = AbstractDungeon.player.cardInUse;
            c.baseDamage += this.amount;
            if (c.baseDamage < 0) c.baseDamage = 0;
            c.applyPowers();
            c.initializeDescription();
        }

        this.isDone = true;
    }
}
