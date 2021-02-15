package theMarked.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theMarked.powers.ChargePower;

import java.util.Iterator;

public class FrictionAction extends AbstractGameAction{
    private AbstractCard card;

    public FrictionAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new ChargePower(p,p,p.hand.size()),p.hand.size()));
        this.isDone = true;
    }
}
