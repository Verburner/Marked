package theMarked.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.cards.Circuit;
import theMarked.powers.ChargePower;
import theMarked.powers.RogueSparksPower;
import theMarked.relics.SecretDocuments;
import theMarked.relics.SpiritSword;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class SummonCircuitAction extends AbstractGameAction{
    private AbstractCard card;
    private boolean upgraded;
    private int number;

    public SummonCircuitAction(AbstractCard card, int amount, int number,  boolean upgraded) {
        this.card = card;
        this.amount = amount;
        this.upgraded = upgraded;
        this.number = number;
    }

    public void update() {

        AbstractCard first = null;

        for (int i = 0;i<number;i++) {
            boolean found = false;
            AbstractCard x = null;
            int place = 0;

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.cardID == card.cardID && c != first && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && !found) {
                    x = c;
                    found = true;
                    place = 1;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.cardID == card.cardID && c != first &&AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && !found) {
                    x = c;
                    found = true;
                    place = 2;
                }
            }


            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID == card.cardID && c != first && !found) {
                    x = c;
                    found = true;
                    place = 3;
                }
            }

            if (!found) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.card, 1));
            } else {
                first = x;
                if (place == 1 || place == 2) {
                    AbstractDungeon.player.hand.addToHand(x);//
                    if (place == 1) AbstractDungeon.player.discardPile.removeCard(x);
                    if (place == 2) AbstractDungeon.player.drawPile.removeCard(x);

                    x.lighten(false);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.glowCheck();
                }

                AbstractPlayer pl = AbstractDungeon.player;
                if (pl.hasRelic(SpiritSword.ID)) {
                    x.setCostForTurn(0);
                }

                if (upgraded) x.upgrade();
                if (amount > 0) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(pl, pl, new ChargePower(pl, pl, amount), amount));
                }
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

            if (AbstractDungeon.player.hasRelic(SecretDocuments.ID)) {
                this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            }
        }
        this.isDone = true;
    }
}