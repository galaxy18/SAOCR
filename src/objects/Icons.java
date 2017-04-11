package objects;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import configs.Params;

@SuppressWarnings("serial")
public class Icons extends Canvas implements Params{
	static final int ba_x = 0;
	static final int ba_y = 1;
	static final int ba_width =  40;
	static final int ba_height = 40;
	static final int ba_spacing = 62;
	static final int ba_NUM_IMAGES = 4;
	
	static final int weapon_x = 0;
	static final int weapon_y = 300;
	static final int weapon_width =  50;
	static final int weapon_height = 50;
	static final int weapon_spacing = 70;
	static final int weapon_NUM_IMAGES = 12;
	
	static final int world_x = 0;
	static final int world_y = 1482;
	static final int world_width =  42;
	static final int world_height = 27;
	static final int world_spacing = 47;
	static final int world_NUM_IMAGES = 3;
	
	static final int rarity_x = 0;
	static final int rarity_y = 1028;
	static final int rarity_width =  57;
	static final int rarity_height = 57;
	static final int rarity_spacing = 57;
	static final int rarity_NUM_IMAGES = 10;

	public Image[] bas = new Image[ba_NUM_IMAGES];
	public Image[] weapons = new Image[weapon_NUM_IMAGES];
	public Image[] worlds = new Image[world_NUM_IMAGES];
	public Image[] raritys = new Image[rarity_NUM_IMAGES];
	
	public Icons(){
		MediaTracker mt = new MediaTracker(this);
		Image image = getToolkit().createImage(image_icon);
		Image[] images = new Image[ba_NUM_IMAGES];
		ImageProducer imageSrc = image.getSource();
		imageSrc = image.getSource();
		if (image != null) {
			ImageFilter filter = null;
			filter = new CropImageFilter(0, 0, 1, 1);
			images[0] = createImage(new FilteredImageSource(imageSrc, filter))
							.getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH);
			
			mt.addImage(images[0], 1);
			
			for (int i = 0; i < ba_NUM_IMAGES - 1; i++) {
				filter = new CropImageFilter(ba_x, ba_y+ba_spacing*i, ba_width, ba_height);
				images[i+1] = createImage(new FilteredImageSource(imageSrc, filter))
								.getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH);
				
				mt.addImage(images[i+1], 1);
			}
            try { mt.waitForAll(); }
            catch (InterruptedException expt) {
                System.out.println("** caught CQImageList.<init>: "+expt);
            };
		}
		
		bas[0] = images[0];
		bas[1] = images[1];
		bas[2] = images[2];
		bas[3] = images[3];
		
		images = new Image[weapon_NUM_IMAGES];
		imageSrc = image.getSource();
		if (image != null) {
			ImageFilter filter = null;
			for (int i = 0; i < weapon_NUM_IMAGES; i++) {
				filter = new CropImageFilter(weapon_x, weapon_y+weapon_spacing*i, weapon_width, weapon_height);
				images[i] = createImage(new FilteredImageSource(imageSrc, filter))
								.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
				
				mt.addImage(images[i], 1);
			}
            try { mt.waitForAll(); }
            catch (InterruptedException expt) {
                System.out.println("** caught CQImageList.<init>: "+expt);
            };
		}
		
		weapons[0] = images[0];
		weapons[1] = images[4];
		weapons[2] = images[5];
		weapons[3] = images[6];
		weapons[4] = images[7];
		weapons[5] = images[8];
		weapons[6] = images[9];
		weapons[7] = images[10];
		weapons[8] = images[11];
		weapons[9] = images[1];
		weapons[10] = images[2];
		weapons[11] = images[3];
		
		images = new Image[world_NUM_IMAGES];
		imageSrc = image.getSource();
		if (image != null) {
			ImageFilter filter = null;
			for (int i = 0; i < world_NUM_IMAGES; i++) {
				filter = new CropImageFilter(world_x, world_y+world_spacing*i, world_width, world_height);
				images[i] = createImage(new FilteredImageSource(imageSrc, filter))
								.getScaledInstance(42,27,java.awt.Image.SCALE_SMOOTH);
				
				mt.addImage(images[i], 1);
			}
            try { mt.waitForAll(); }
            catch (InterruptedException expt) {
                System.out.println("** caught CQImageList.<init>: "+expt);
            };
		}
		
		worlds[0] = images[0];
		worlds[1] = images[1];
		worlds[2] = images[2];

		image = getToolkit().createImage(image_replace);
		images = new Image[rarity_NUM_IMAGES*2];
		imageSrc = image.getSource();
		if (image != null) {
			for (int i = 0; i < rarity_NUM_IMAGES*2; i++) {
				ImageFilter filter = null;
				filter = new CropImageFilter(rarity_x, rarity_y+rarity_spacing*i, rarity_width, rarity_height);
				images[i] = createImage(new FilteredImageSource(imageSrc, filter))
								.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
				
				mt.addImage(images[i], 1);
			}
            try { mt.waitForAll(); }
            catch (InterruptedException expt) {
                System.out.println("** caught CQImageList.<init>: "+expt);
            };
		}
		
		raritys[0] = images[0];
		raritys[1] = images[0];
		raritys[2] = images[1];
		raritys[3] = images[2];
		raritys[4] = images[4];
		raritys[5] = images[6];
		raritys[6] = images[8];
		raritys[7] = images[10];
		raritys[8] = bas[0];
		raritys[9] = bas[0];
	}
}
