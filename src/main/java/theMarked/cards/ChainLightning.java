package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class ChainLightning extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ChainLightning.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_ChainLightning.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 2;


    public int specialDamage;

    // /STAT DECLARATION/

    public ChainLightning() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new CastAnimationAction());
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.LIGHTNING));
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        this.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            if (mo!=m && !mo.isDead) {
                this.calculateCardDamage(mo);
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(mo, new DamageInfo(p, damage / 2, damageTypeForTurn),
                                AbstractGameAction.AttackEffect.LIGHTNING, true));
                this.addToTop(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.0F));
                this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}