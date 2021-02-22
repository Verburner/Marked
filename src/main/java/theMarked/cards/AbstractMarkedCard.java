package theMarked.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractMarkedCard extends AbstractDynamicCard {
    public String betaArtPath;

    public AbstractMarkedCard(final String id,
                               final String img,
                               final int cost,
                               final AbstractCard.CardType type,
                               final AbstractCard.CardColor color,
                               final AbstractCard.CardRarity rarity,
                               final AbstractCard.CardTarget target) {

        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }
}
