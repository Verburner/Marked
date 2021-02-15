package theMarked.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theMarked.DefaultMod;
import theMarked.VFX.AttractMetalEffect;
import theMarked.actions.CastAnimationAction;
import theMarked.characters.TheMarked;

import static theMarked.DefaultMod.makeCardPath;

public class AttractMetal extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(AttractMetal.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill_AttractMetal.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheMarked.Enums.MARKED_GENTA;

    private static final int COST = 1;
    private static final int BLOCK = 8;

    // /STAT DECLARATION/


    public AttractMetal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.block = baseBlock;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CastAnimationAction());
        int bblock = 0;
        if (m.currentBlock > 0)
        {
            bblock = m.currentBlock;
            if (this.upgraded) m.loseBlock(bblock);
        }
        for (int i=0;i<bblock;i++)
        {
            this.addToBot(new VFXAction(new AttractMetalEffect(m.hb.cX, m.hb.cY)));
            this.addToBot(new WaitAction(0.02F));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,block));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        int bblock = mo.currentBlock;
        this.baseBlock = baseBlock+bblock;
        super.calculateCardDamage(mo);
        this.baseBlock-=bblock;
        isBlockModified = baseBlock != block;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
