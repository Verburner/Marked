package theMarked.VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AttractMetalEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private static final float DUR = 0.1F;
    private boolean playedSound = false;
    private boolean forcedAngle = false;
    private static Texture shoe;
    private static TextureAtlas.AtlasRegion shoe_img;
    private float path;
    private float xscale;
    private float yscale;

    public AttractMetalEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.2F + MathUtils.random(0.0F, 0.4F);;
        this.duration = startingDuration;
        this.scale = Settings.scale;
        this.rotation = MathUtils.random(1.0F, 360.0F);
        this.xscale = 0.6F + MathUtils.random(0.0F, 0.4F);
        this.yscale = 0.6F + MathUtils.random(0.0F, 0.4F);
        float color_intesity = 150.0F + MathUtils.random(0.0F, 105.0F);
        this.color = CardHelper.getColor(color_intesity,color_intesity,color_intesity);

        shoe = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/metal.png");
        shoe_img = new TextureAtlas.AtlasRegion(shoe,0,0,100,100);
    }

    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("ATTACK_FAST");
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        path = duration/startingDuration;
        sb.draw(this.shoe_img, this.x - ((1000.0F-1000.0F*path) * Settings.scale), this.y, (float)this.shoe_img.packedWidth / 2.0F, (float)this.shoe_img.packedHeight / 2.0F, (float)this.shoe_img.packedWidth, (float)this.shoe_img.packedHeight, this.scale*xscale, this.scale*yscale, this.rotation);
        sb.setBlendFunction(770, 1);
    }

    public void dispose() {
    }
}
