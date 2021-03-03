package theMarked.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.cards.AlyssasBlade;
import theMarked.cards.RogueSparks;
import theMarked.characters.TheMarked;
import theMarked.powers.RogueSparksPower;
import theMarked.relics.SecretDocuments;

public class SummonAction extends AbstractGameAction{
    private AbstractCard card;

    public SummonAction(AbstractCard card) {this.card = card;}

    public void update() {

        if (AbstractDungeon.player instanceof TheMarked) {
            ((TheMarked) AbstractDungeon.player).swordBuff();
        }
        boolean found = false;
        AbstractCard x = null;
        ePlace place = ePlace.NOWHERE;

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AlyssasBlade && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && !found) {
                x = c;
                found = true;
                place = ePlace.DISCARD_PILE;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AlyssasBlade && !found) {
                x = c;
                found = true;
                place = ePlace.HAND;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AlyssasBlade && !found) {
                x = c;
                found = true;
                place = ePlace.DRAW_PILE;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            if (c instanceof AlyssasBlade && !found) {
                x = c;
                found = true;
                place = ePlace.LIMBO;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AlyssasBlade && !found) {
                x = c;
                found = true;
                place = ePlace.EXHAUST_PILE;
            }
        }
        for (CardQueueItem cq : AbstractDungeon.actionManager.cardQueue) {
            if (cq.card instanceof AlyssasBlade)
            {
                x = cq.card;
                found = true;
                place = ePlace.CARD_QUEUE;

            }
        }
        if (AbstractDungeon.player.cardInUse instanceof AlyssasBlade)
        {
            x = AbstractDungeon.player.cardInUse;
            found = true;
            place = ePlace.IN_USE;
        }

        if (!found)
        {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(this.card, 1));
        }
        else if (found)
        {
            if (place == ePlace.DRAW_PILE || place == ePlace.DISCARD_PILE || place == ePlace.EXHAUST_PILE)
            {
                AbstractDungeon.player.hand.addToHand(x);
                if (place == ePlace.DISCARD_PILE) AbstractDungeon.player.discardPile.removeCard(x);
                if (place == ePlace.DRAW_PILE) AbstractDungeon.player.drawPile.removeCard(x);
                if (place == ePlace.EXHAUST_PILE) AbstractDungeon.player.exhaustPile.removeCard(x);

                x.lighten(false);
                if (place == ePlace.EXHAUST_PILE) {
                    x.unhover();
                    x.fadingOut = false;
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.glowCheck();
            }
            if (place != ePlace.EXHAUST_PILE && AbstractDungeon.player.hasRelic("theMarked:SpiritSword")) {
                this.addToBot(new ReduceCostAction(x.uuid, x.cost));
            }
        }


        if (AbstractDungeon.player.hasRelic(SecretDocuments.ID)) {
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
        if (AbstractDungeon.player.hasPower(RogueSparksPower.POWER_ID)) {
            int dmg = AbstractDungeon.player.getPower(RogueSparksPower.POWER_ID).amount;
            int block = ((RogueSparksPower)AbstractDungeon.player.getPower(RogueSparksPower.POWER_ID)).amount2;
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null) {
                this.addToTop(new DamageAction(m, new DamageInfo(m, dmg, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
                this.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
                this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
            this.addToBot(new GainBlockAction(AbstractDungeon.player, block));
        }
        this.isDone = true;
    }
}