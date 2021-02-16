package theMarked.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.cards.AlyssasBlade;

public class DiscardSwordAction extends AbstractGameAction{

    public DiscardSwordAction() {}

    public void update() {
        AlyssasBlade b = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AlyssasBlade) {
                GameActionManager.incrementDiscard(false);
                b = (AlyssasBlade)c;
            }
        }
        if (b!=null) {
            AbstractDungeon.player.hand.removeCard(b);
            b.moveToDiscardPile();
            AbstractDungeon.player.discardPile.addToTop(b);
        }
        this.isDone = true;
    }
}