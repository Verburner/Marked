package theMarked.VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SwordBuffEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private static final float DUR = 1.0F;
    private static TextureAtlas.AtlasRegion img1;
    private static TextureAtlas.AtlasRegion img2;
    private static TextureAtlas.AtlasRegion img3;
    private boolean playedSound = false;
    private boolean forcedAngle = false;
    private static Texture sword;
    private static Texture shine1;
    private static Texture shine2;
    private float path;
    private Color color2;
    private float alpha;

    public SwordBuffEffect(float x, float y, Color c) {
        this.x = x-250*Settings.scale;
        this.y = y+100*Settings.scale;
        this.startingDuration = 1.5F;
        this.duration = 1.5F;
        this.scale = Settings.scale;
        this.color = c;
        this.color2 = Color.WHITE.cpy();
        this.alpha = 0.0F;

        sword = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/sword.png");
        img1 = new TextureAtlas.AtlasRegion(sword,0,0,400,400);
        shine1 = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/buff1.png");
        img2 = new TextureAtlas.AtlasRegion(shine1,0,0,400,400);
        shine2 = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/buff2.png");
        img3 = new TextureAtlas.AtlasRegion(shine2,0,0,400,400);
    }

    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (startingDuration-duration<0.2)
        {
            alpha = (alpha+19)/20;
        }
        else if (startingDuration-duration>1.4F)
        {
            alpha = alpha/1.2F;
        }
        else alpha = 1.0F;
    }

    public void render(SpriteBatch sb) {
        this.color.a=alpha;
        this.color2.a=alpha;
        sb.setColor(this.color);
        path = duration/startingDuration;
        sb.draw(this.img2, this.x, this.y, (float)this.img1.packedWidth / 2.0F, (float)this.img1.packedHeight / 2.0F, (float)this.img1.packedWidth, (float)this.img1.packedHeight, this.scale, this.scale, this.rotation+(60.0F*this.duration));
        sb.draw(this.img3, this.x, this.y, (float)this.img1.packedWidth / 2.0F, (float)this.img1.packedHeight / 2.0F, (float)this.img1.packedWidth, (float)this.img1.packedHeight, this.scale, this.scale, this.rotation-(90.0F*this.duration));
        sb.setColor(Color.WHITE.cpy());
        sb.draw(this.img1, this.x, this.y, (float)this.img1.packedWidth / 2.0F, (float)this.img1.packedHeight / 2.0F, (float)this.img1.packedWidth, (float)this.img1.packedHeight, this.scale*0.8F, this.scale*0.8F, this.rotation);
        sb.setBlendFunction(770, 1);
    }

    public void dispose() {
    }
}
