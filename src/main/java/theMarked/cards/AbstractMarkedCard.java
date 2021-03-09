package theMarked.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theMarked.DefaultMod;
import theMarked.util.TextureLoader;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractMarkedCard extends AbstractDynamicCard {
    public String IMG_beta;

    public AbstractMarkedCard(final String id,
                               final String img,
                               final int cost,
                               final AbstractCard.CardType type,
                               final AbstractCard.CardColor color,
                               final AbstractCard.CardRarity rarity,
                               final AbstractCard.CardTarget target) {

        super(id, img, cost, type, color, rarity, target);
    }

    public void loadBetaImage(String img) {
        Texture cardTexture;
        if (imgMap.containsKey(img)) {
            cardTexture = (Texture)imgMap.get(img);
        } else {
            cardTexture = ImageMaster.loadImage(img);
            imgMap.put(img, cardTexture);
        }

        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "jokePortrait", cardImg);
    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (IMG_beta != null) {
                    int endingIndex = IMG_beta.lastIndexOf(".");
                    String newPath = IMG_beta.substring(0, endingIndex) + "_p" + IMG_beta.substring(endingIndex);
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
