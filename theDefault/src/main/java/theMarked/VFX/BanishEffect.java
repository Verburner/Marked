package theMarked.VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BanishEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private static final float DUR = 1.0F;
    private TextureAtlas.AtlasRegion img1;
    private TextureAtlas.AtlasRegion img2;
    private boolean playedSound = false;
    private boolean forcedAngle = false;
    private static Texture banish1;
    private static Texture banish2;
    private float rise1;
    private float rise2;
    private float constrict;
    private float constricted = 0.2F;
    private float size;
    private float height;
    private float average_size = 200.0F;
    private boolean isPlayedSound = false;

    public BanishEffect(float x, float y, float size, float height) {
        this.x = x;
        this.y = y;
        this.startingDuration = 1.0F;
        this.duration = 1.0F;
        this.scale = Settings.scale;
        this.color = Color.WHITE.cpy();
        this.size = size/average_size;
        this.height = height/average_size;
        rise1 = 0.0F;
        rise2 = 0.0F;
        constrict = 1.0F;

        banish1 = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/banish1.png");
        img1 = new TextureAtlas.AtlasRegion(banish1,0,0,400,400);
        banish2 = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/banish2.png");
        img2 = new TextureAtlas.AtlasRegion(banish2,0,0,400,400);
    }

    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        //if (duration > 0.5F) rise1 = (0.5F-(duration-0.5F))*2.0F;
        if (duration > 0.5F) rise1 = (rise1*24+1)/25;
        else (rise1) = 1.0F;
        //if (duration > 0.25F) rise2 = (0.75F-(duration-0.25F))*4.0F;
        if (duration > 0.25F) rise2 = (rise2*14+1)/15;
        else (rise2) = 1.0F;

        if (duration <0.4F && constricted > 0.0F)
        {
            if (!isPlayedSound)
            {
                CardCrawlGame.sound.play("POWER_CONSTRICTED");
                isPlayedSound = true;
            }
            constrict /= 1.03;
            constricted -= Gdx.graphics.getDeltaTime();
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img2, this.x-(float)this.img1.packedWidth/2.0F, this.y-this.img1.packedHeight/2.0F+rise1*this.img1.packedHeight/4.0F, (float)this.img1.packedWidth / 2.0F, (float)this.img1.packedHeight / 2.0F, (float)this.img1.packedWidth, (float)this.img1.packedHeight, this.scale*constrict*size, this.scale*rise1*height, this.rotation);
        sb.draw(this.img1, this.x-(float)this.img1.packedWidth/2.0F, this.y-this.img1.packedHeight/2.0F+rise2*this.img1.packedHeight/4.0F, (float)this.img1.packedWidth / 2.0F, (float)this.img1.packedHeight / 2.0F, (float)this.img1.packedWidth, (float)this.img1.packedHeight, this.scale*constrict*size, this.scale*rise2*height, this.rotation);
        sb.setBlendFunction(770, 1);
    }

    public void dispose() {
    }
}
