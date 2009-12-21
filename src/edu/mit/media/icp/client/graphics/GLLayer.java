package edu.mit.media.icp.client.graphics;

import java.io.BufferedReader;
import java.io.StringReader;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;


public class GLLayer extends GLSurfaceView{
	String bundleContents = "# Bundle file v0.3\n23 67\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n-8.5252010315e+02 -2.4073810947e-02 -2.4649955630e-02\n9.9554361042e-01 -6.9514140547e-03 -9.4045720736e-02\n-3.2624124848e-02 9.1031139884e-01 -4.1263643030e-01\n8.8479298279e-02 4.1386572095e-01 9.0602791281e-01\n2.8378189143e-01 1.2669121984e+00 1.3439922906e+00\n-8.9691525663e+02 -2.4809716322e-02 7.5710678502e-03\n9.7959124309e-01 1.5153037909e-01 1.3205885307e-01\n-8.6214563941e-02 9.1026386843e-01 -4.0495276119e-01\n-1.8157104787e-01 3.8530278229e-01 9.0475063997e-01\n-6.6001507002e-01 1.3610975620e+00 1.6991601480e+00\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n0 0 0\n1.0658657183e+00 4.0170805391e-01 3.0815681254e+00\n194 72 37\n2 16 1182 203.7200 63.1700 17 1097 171.5800 77.9300\n5.1140141692e-01 7.9046507199e-01 3.3468144333e+00\n38 5 4\n2 16 1226 84.8000 105.4900 17 1139 73.0900 123.7800\n5.0128943705e-01 8.0493610180e-01 3.3265243684e+00\n122 108 116\n2 16 1233 83.5500 109.7200 17 1535 71.3600 128.1000\n-7.2568567243e-01 -9.8009149896e-01 2.6098849057e+00\n226 226 237\n2 16 144 -177.8700 -178.1500 17 173 -275.4700 -123.2400\n-1.3085767384e+00 -7.5904783268e-01 2.7728680650e+00\n251 142 89\n2 16 208 -315.6600 -130.1600 17 260 -363.6600 -73.1200\n-1.6338334192e+00 1.3777704366e-01 3.2129132522e+00\n132 135 134\n2 16 864 -335.2200 24.3600 17 1445 -327.4600 58.9200\n-1.5954623538e+00 4.6106311801e-01 3.3458641276e+00\n86 81 83\n2 16 906 -311.3600 68.6400 17 1119 -294.6600 97.1000\n4.9675375759e-01 7.4273233383e-01 3.3748758780e+00\n18 7 5\n2 16 671 81.7000 95.5000 17 615 69.7200 113.9700\n-8.2662410862e-01 -7.6888783700e-01 2.3428994499e+00\n79 78 86\n2 16 1063 -208.6500 -103.0700 17 984 -310.9100 -52.7500\n4.8664968973e-01 -5.5263289415e-01 2.5968677179e+00\n184 70 19\n2 16 812 128.0700 -78.5600 17 729 18.1700 -56.3100\n1.0413119471e+00 4.1178874661e-01 3.0932654083e+00\n185 72 41\n2 16 1185 198.2100 64.0600 17 1099 166.6500 78.8500\n1.6775619318e+00 6.5018435124e-01 3.2326046891e+00\n70 73 80\n2 16 1634 297.9700 85.2500 17 1495 295.2600 97.6100\n1.8915516319e-02 -5.0247063852e-01 2.8176498805e+00\n219 189 153\n2 16 1445 9.5000 -81.5600 17 1318 -76.4900 -53.0300\n1.0033156610e+00 -1.9916460016e-01 2.7054131372e+00\n188 192 198\n2 16 1523 230.4500 -14.2600 17 1391 149.8300 -0.4700\n9.9774615956e-01 -1.8709238164e-01 2.7015247484e+00\n112 111 127\n2 16 1525 229.2200 -11.3300 17 1034 148.7900 2.4700\n1.6916292636e+00 5.1594262511e-01 3.2601657997e+00\n53 54 56\n2 16 1600 302.1100 61.4200 17 1086 296.6200 71.6900\n-4.2964107360e-01 1.9554382257e+00 4.4313236404e+00\n69 83 85\n2 16 943 -79.7700 171.3300 17 1182 -27.3100 189.4000\n-1.6817573228e+00 9.4096459354e-01 2.8568408971e+00\n104 104 109\n2 16 1775 -338.1200 202.3000 17 1617 -322.3600 218.0900\n-1.6999534051e+00 6.3854643296e-01 2.9832247085e+00\n211 203 201\n2 16 14 -345.0900 137.9300 17 27 -330.9100 157.9000\n-1.1773699274e+00 8.6563584907e-01 3.3894039478e+00\n182 183 188\n2 16 28 -220.9900 125.8300 17 26 -207.9900 148.7100\n-2.6603065010e-01 -7.8343078722e-01 2.7253771332e+00\n112 55 36\n2 16 36 -57.0100 -138.1500 17 30 -155.6400 -99.1700\n-1.6962726554e+00 4.6190290076e-01 3.1470286403e+00\n51 61 60\n2 16 59 -341.2500 89.2000 17 49 -326.0900 115.7500\n-7.1895921649e-02 1.8295295011e-01 3.0060208030e+00\n254 144 67\n2 16 80 -14.7900 40.2100 17 66 -60.8900 63.1000\n-9.8080646100e-01 4.4078052336e-01 3.2174692453e+00\n85 83 79\n2 16 122 -195.1400 72.6100 17 149 -203.9300 98.4700\n-1.6706216792e+00 4.1647378669e-01 3.3156553495e+00\n252 235 232\n2 16 180 -328.8600 64.5700 17 218 -310.9600 93.7300\n-1.4045801778e+00 8.1480180588e-01 3.4550195703e+00\n143 144 146\n2 16 265 -262.0400 114.3300 17 331 -241.5800 136.4800\n-1.3168211381e+00 -9.3638441291e-01 2.7297070807e+00\n24 21 18\n2 16 291 -326.9100 -171.4400 17 373 -381.8900 -106.2800\n-1.3196307629e+00 -8.7302624484e-01 2.7430802678e+00\n53 48 46\n2 16 295 -324.4200 -156.0500 17 382 -376.6600 -94.1700\n1.1633003547e+00 9.1582283914e-01 3.4626316017e+00\n136 160 149\n2 16 515 190.3200 108.8500 17 454 193.6500 124.4800\n1.3522673200e+00 -1.0169116025e+00 2.5771735853e+00\n199 175 145\n2 16 556 349.7100 -192.0800 17 351 224.3000 -191.1300\n2.2647756722e-02 -5.1872873245e-01 2.8293166165e+00\n42 44 49\n2 16 601 10.1200 -85.9900 17 398 -75.8200 -57.4000\n1.3369080398e+00 -5.6040478570e-01 2.5582920070e+00\n112 116 120\n2 16 604 329.6100 -81.8800 17 714 227.3000 -75.6600\n1.0385090220e+00 1.8281907510e-01 3.0533201289e+00\n94 76 72\n2 16 646 204.7900 27.1100 17 578 162.6600 42.2100\n-1.5675056893e+00 4.8210226623e-01 3.3387003446e+00\n232 231 230\n2 16 657 -305.6300 72.8700 17 820 -289.7800 100.3000\n-1.7433979632e+00 2.8848574771e-01 2.9869647011e+00\n118 124 122\n2 16 658 -366.2400 75.0600 17 450 -356.9400 104.0000\n-1.7433979632e+00 2.8848574771e-01 2.9869647011e+00\n118 124 122\n2 16 659 -366.2400 75.0600 17 451 -356.9400 104.0000\n1.0269926302e+00 -8.9945174496e-01 2.3929227445e+00\n78 79 84\n2 16 763 285.8100 -150.2600 17 667 141.3500 -138.8300\n1.2774292242e+00 -6.9119388312e-01 2.5656877008e+00\n161 160 170\n2 16 782 320.2600 -112.1500 17 690 209.8200 -106.2300\n-7.6745450303e-01 -7.6571992559e-01 2.3403034878e+00\n81 85 97\n2 16 790 -192.0800 -102.4300 17 983 -297.7900 -53.1800\n7.3908599689e-01 -3.5999874019e-01 2.7643208501e+00\n71 72 77\n2 16 828 172.3700 -51.2000 17 1010 85.5200 -34.0100\n6.2637036588e-01 6.8398303996e-01 3.3725068376e+00\n46 21 12\n2 16 913 105.2700 85.7700 17 824 91.9400 103.2600\n6.6608799958e-01 -9.7179485956e-01 2.6277091683e+00\n25 27 39\n2 16 999 177.7000 -182.2200 17 1221 48.1300 -161.2200\n8.7865419420e-01 -5.3621215969e-01 2.5540727975e+00\n129 130 139\n2 16 1091 223.2600 -73.5500 17 977 112.3700 -58.3200\n8.2422957549e-01 -5.3761830161e-01 2.5557729072e+00\n132 133 145\n2 16 1093 210.3800 -73.7000 17 979 98.9700 -57.4800\n9.3212765214e-01 -3.9988630362e-01 2.5659517786e+00\n237 238 243\n2 16 1118 230.9200 -44.3700 17 1021 128.8400 -29.5100\n7.2251145974e-01 -1.9908650386e-01 2.7726786394e+00\n118 122 134\n2 16 1131 165.0500 -18.3400 17 761 86.0400 -1.0300\n1.3942410131e+00 7.7991931343e-02 3.0503643420e+00\n11 10 15\n2 16 1147 276.0500 6.6400 17 771 236.8400 16.2800\n-6.1784489633e-02 6.8009306692e-02 3.0565042608e+00\n165 48 5\n2 16 1149 -13.5300 14.4600 17 1429 -61.0800 37.8500\n1.1105245517e+00 1.2985956372e-01 3.0274895026e+00\n188 52 0\n2 16 1154 221.5900 20.2300 17 776 177.1000 32.7200\n1.1105245517e+00 1.2985956372e-01 3.0274895026e+00\n188 52 0\n2 16 1155 221.5900 20.2300 17 777 177.1000 32.7200\n1.5071343229e+00 2.2867588297e-01 3.0553650560e+00\n214 95 54\n2 16 1162 292.7500 32.2800 17 1057 262.4600 42.3200\n1.6844324047e+00 5.7159588793e-01 3.2704232198e+00\n180 183 192\n2 16 1190 298.5400 69.3200 17 1100 295.0800 80.5300\n3.8192510303e-02 5.8660672794e-01 3.2346309343e+00\n127 124 136\n2 16 1205 2.5500 87.6900 17 1128 -19.7200 107.6500\n3.8562706390e-01 7.3643908980e-01 3.3779005190e+00\n193 83 45\n2 16 1214 61.9600 95.3500 17 1137 49.6900 113.7400\n8.4734329784e-01 1.0155215976e+00 3.6304972634e+00\n78 108 103\n2 16 1234 129.3400 110.6700 17 1142 137.8500 127.2400\n-8.0126760859e-01 1.5718597867e+00 4.2932149558e+00\n34 49 71\n2 16 1258 -136.0000 139.3400 17 1558 -90.4900 158.9100\n-4.5668404618e-01 1.9608544746e+00 4.4381897678e+00\n201 222 242\n2 16 1275 -83.5200 171.4600 17 1183 -30.6400 189.7400\n-1.8378871737e+00 6.4999511707e-01 2.6785816527e+00\n102 105 110\n2 16 1280 -393.1500 177.0100 17 1583 -380.6500 195.0300\n-8.1395024026e-01 -9.5630500810e-01 2.5402409633e+00\n45 49 53\n2 16 1348 -203.0600 -167.4800 17 1254 -299.9000 -110.6000\n4.4620546273e-01 -6.4955792603e-01 2.7990556928e+00\n37 14 13\n2 16 1411 109.4800 -115.6400 17 698 11.1500 -92.0500\n-7.0046107418e-02 -2.9228155871e-01 2.8886825402e+00\n203 90 48\n2 16 1500 -12.3600 -42.0000 17 1370 -83.3200 -14.6100\n7.0409927720e-01 -2.0777292898e-01 2.7658419697e+00\n175 180 195\n2 16 1520 161.6300 -19.1700 17 1387 81.6100 -2.0500\n7.0409927720e-01 -2.0777292898e-01 2.7658419697e+00\n175 180 195\n2 16 1521 161.6300 -19.1700 17 1388 81.6100 -2.0500\n1.4817856576e+00 2.9989192136e-02 3.0368832846e+00\n19 19 27\n2 16 1532 295.3000 -1.5600 17 1398 255.8000 6.6900\n4.1105053673e-01 9.7325186978e-02 2.9318006788e+00\n255 142 54\n2 16 1558 87.0900 27.6500 17 1434 30.0500 47.2000\n1.6775619318e+00 6.5018435124e-01 3.2326046891e+00\n70 73 80\n2 16 1635 297.9700 85.2500 17 1496 295.2600 97.6100\n1.6493179308e+00 7.2264170014e-01 3.3382136130e+00\n124 124 132\n2 16 1639 283.6400 87.3600 17 1504 286.6900 99.6900";

	public GLLayer(Context context) {
		super(context);
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		//setRenderer(new CubeRenderer(true));
		//InputStream is = Resources.getSystem().openRawResource(R.raw.bundle);
		//BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringReader string = new StringReader(bundleContents);
		BufferedReader reader = new BufferedReader(string);
		setRenderer(new ICPRenderer(new Bundle(reader), true));
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
}
