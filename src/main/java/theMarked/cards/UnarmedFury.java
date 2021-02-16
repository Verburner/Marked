package theMarked.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.characters.TheMarked;

import java.util.Iterator;

import static theMarked.DefaultMod.makeCardPath;

public class UnarmedFury extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 9(+3) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(UnarmedFury.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack_UnarmedFury.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 0;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 2;

    public int specialDamage;

    // /STAT DECLARATION/

    public UnarmedFury() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            boolean found = false;
            Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

            AbstractCard c;
            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                if (c instanceof AlyssasBlade) {
                    found = true;
                }
            }

            var1 = AbstractDungeon.player.drawPile.group.iterator();

            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                if (c instanceof AlyssasBlade) {
                    found = true;
                }
            }

            var1 = AbstractDungeon.player.hand.group.iterator();

            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                if (c instanceof AlyssasBlade) {
                    found = true;
                }
            }
            if (found)
            {
                this.cantUseMessage = EXTENDED_DESCRIPTION;
                return false;
            }
            else return true;
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