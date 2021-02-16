package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theMarked.DefaultMod;
import theMarked.actions.CastAnimationAction;
import theMarked.actions.SummonAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class SwordAndMagic extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SwordAndMagic.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_SwordAndMagic.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public int specialDamage;

    // /STAT DECLARATION/

    public SwordAndMagic() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        this.cardsToPreview = new AlyssasBlade();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new CastAnimationAction());
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.LIGHTNING));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m,1,false), 1));
        AbstractDungeon.actionManager.addToBottom(new SummonAction(new AlyssasBlade()));
    }
    @Override
    public void hover()
    {
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.cardsToPreview = this.searchBlade();
        } else this.cardsToPreview = new AlyssasBlade();
        super.hover();
    }

    private AbstractCard searchBlade()
    {
        AbstractCard x = new AlyssasBlade();
        AbstractCard ca = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c instanceof AlyssasBlade) ca = c;
        }
        if (ca != null)
        {
            x.baseDamage = ca.baseDamage;
            x.baseMagicNumber = ca.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber = ((AbstractDynamicCard)ca).defaultBaseSecondMagicNumber;
            x.baseBlock = ca.baseBlock;

            x.damage = x.baseDamage;
            x.magicNumber = x.baseMagicNumber;
            ((AbstractDynamicCard)x).defaultSecondMagicNumber = ((AbstractDynamicCard)x).defaultBaseSecondMagicNumber;
            x.block = x.baseBlock;
            x.initializeDescription();
            return x;
        }
        return new AlyssasBlade();
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