<template>
  <div class="coupon-map-container">
    <div class="info-box">
      <div class="header">
        <span class="title">最近的肯德基</span>
        <button class="map-button" @click="openGoogleMaps">地圖</button>
      </div>
      <div class="details">
        <p v-if="nearestKfc">
          <span class="store-info">店名：{{ nearestKfc.name }}</span> |
          <span class="store-info">地址：{{ nearestKfc.address }}</span>
        </p>
        <p v-else>
          無法取得最近的肯德基，請確認定位已開啟。
        </p>
      </div>
    </div>
    <a href="https://www.kfcclub.com.tw/Coupon" target="_blank" class="action-button" title="前往KFC官網~">
      現在就行動
    </a>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";

export default {
  setup() {
    const userLocation = ref(null);
    const nearestKfc = ref(null);

    const kfcStores = [
      { name: "基隆忠二餐廳（基隆港海洋廣場前）", address: "基隆市仁愛區忠二路13號" ,lat: 25.128321, lng: 121.739369},
  { name: "基隆仁一餐廳（田寮河銀蛇橋前）", address: "基隆市仁愛區劉銘傳路1號" ,lat: 25.126487, lng: 121.749616},
  { name: "基隆深溪餐廳（12/10開幕）", address: "基隆市信義區深溪路156、158號" ,lat: 25.132805, lng: 121.781281},
{ name: "天母中山餐廳（美國學校）", address: "台北市士林區中山北路六段748、750號" ,lat: 25.115654, lng: 121.528296},
{ name: "士林文昌餐廳", address: "台北市士林區中正路351號" ,lat: 25.09431, lng: 121.522311},
{ name: "台北士林餐廳（劍潭捷運站1號）", address: "台北市士林區文林路88號" ,lat: 25.087298, lng: 121.525969},
{ name: "台北承德餐廳（京站對面）", address: "台北市大同區承德路一段38號" ,lat: 25.050879, lng: 121.516704},
{ name: "台北復興南餐廳", address: "台北市大安區復興南路二段183號" ,lat: 25.026808, lng: 121.543717},
{ name: "台北中山餐廳（南京復興捷運站3號）", address: "台北市中山區南京東路三段222號" ,lat: 25.051608, lng: 121.543599},
{ name: "台北中崙餐廳（中崙大潤發B1）", address: "台北市中山區八德路二段306號B1" ,lat: 25.046873, lng: 121.542555},
{ name: "台北南京東三餐廳", address: "台北市中山區南京東路一段88號" ,lat: 25.051768, lng: 121.526206},
{ name: "台北雙連餐廳（雙連捷運站2號）", address: "台北市中山區民生西路9號" ,lat: 25.057916, lng: 121.521646},
{ name: "台北八德餐廳（光華商場）", address: "台北市中正區八德路一段64號" ,lat: 25.043876, lng: 121.531424},
{ name: "台北南昌餐廳", address: "台北市中正區南昌路一段12號" ,lat: 25.032246, lng: 121.516313},
{ name: "內湖金龍餐廳", address: "台北市內湖區金龍路8號" ,lat: 25.084482, lng: 121.59442},
{ name: "內湖瑞光餐廳", address: "台北市內湖區瑞光路292號" ,lat: 25.076126, lng: 121.575087},
{ name: "台北東湖三餐廳", address: "台北市內湖區東湖路138號" ,lat: 25.067955, lng: 121.616823},
{ name: "台北內湖餐廳（西湖捷運站）", address: "台北市內湖區內湖路一段230號" ,lat: 25.082303, lng: 121.56483},
{ name: "文山興隆餐廳（近萬芳醫院捷運站）", address: "台北市文山區興隆路三段9號" ,lat: 25.000881, lng: 121.554885},
{ name: "文山木柵餐廳（近文山區行政中心）", address: "台北市文山區木柵路三段82號" ,lat: 24.988492, lng: 121.566806},
{ name: "北投光明餐廳（新北投捷運站）", address: "台北市北投區光明路225號" ,lat: 25.136549, lng: 121.504124},
{ name: "台北石牌二餐廳（數位未來店）", address: "台北市北投區裕民一路6號" ,lat: 25.116639, lng: 121.516332},
{ name: "北投中央餐廳", address: "台北市北投區中央北路四段525號" ,lat: 25.127958, lng: 121.468123},
{ name: "台北南京東二餐廳（南京三民捷運站2號）", address: "台北市松山區南京東路五段164號" ,lat: 25.051307, lng: 121.563115},
{name: "台北莊敬餐廳", address: "台北市信義區莊敬路336號1樓" ,lat: 25.027787, lng: 121.563802},
{name: "台北永春餐廳（永春捷運站出口）", address: "台北市信義區忠孝東路五段297號B1" ,lat: 25.041194, lng: 121.573811},
{name: "台北光復餐廳", address: "台北市信義區光復南路465號1F",lat: 25.034025, lng: 121.557716},
{name: "台北南港餐廳（內用暫不開放）", address: "台北市南港區三重路11號1-2樓",lat: 25.055798, lng: 121.613807},
{name: "台北萬大餐廳", address: "台北市萬華區萬大路286號",lat: 25.025437, lng: 121.500277},
{name: "新北八里餐廳", address: "新北市八里區中華路二段512號",lat: 25.145456, lng: 121.399527},
{name: "三重三和餐廳（數位未來店）（徐匯捷運站）", address: "新北市三重區三和路四段193-195號",lat: 25.079257, lng: 121.482278},
{name: "三重重新二餐廳（數位未來店）", address: "新北市三重區重新路二段1-1號",lat: 25.063034, lng: 121.499554},
{name: "三重正義餐廳", address: "新北市三重區正義北路139-141號",lat: 25.066716, lng: 121.496733},
{name: "三峽北大餐廳（台北大學）", address: "新北市三峽區大學路150號",lat: 24.944583, lng: 121.374391},
{name: "土城中央餐廳", address: "新北市土城區中央路二段174號",lat: 24.97632, lng: 121.44179},
{name: "土城金城餐廳（土城看守所旁）", address: "新北市土城區金城路二段269號及271號",lat: 24.986028, lng: 121.463903},
{name: "中和景安餐廳", address: "新北市中和區景安路114號1樓",lat: 24.995216, lng: 121.505308},
{name: "中和興南餐廳（興南夜市旁）", address: "新北市中和區興南路一段79號",lat: 24.989105, lng: 121.510108},
{name: "中和福祥餐廳（中和大潤發）", address: "新北市中和區福善里中山路2段228-1號",lat: 25.002743, lng: 121.498848},
{name: "中和員山餐廳", address: "新北市中和區員山路202號",lat: 25.000642, lng: 121.481453},
{name: "五股成泰餐廳", address: "新北市五股區成泰路二段113號",lat: 25.084604, lng: 121.43845},
{name: "永和中正餐廳", address: "新北市永和區中正路294號",lat: 24.999011, lng: 121.51725},
{name: "永和中山餐廳（樂華夜市旁）", address: "新北市永和區中山路一段173、175號",lat: 25.009146, lng: 121.509803},
{name: "汐止中興餐廳", address: "新北市汐止區中興路169號",lat: 25.066303, lng: 121.630794},
{name: "汐止大同餐廳", address: "新北市汐止區大同路二段383號",lat: 25.067367, lng: 121.657631},
{name: "林口文化餐廳", address: "新北市林口區文化一路一段78號",lat: 25.069791, lng: 121.371875},
{name: "板橋忠孝餐廳", address: "新北市板橋區忠孝路21號",lat: 25.002057, lng: 121.460382},
{name: "板橋雙十餐廳", address: "新北市板橋區雙十路2段170號",lat: 25.029693, lng: 121.473023},
{name: "板橋中山二餐廳", address: "新北市板橋區中山路一段65號",lat: 25.009763, lng: 121.461124},
{name: "板橋新埔餐廳", address: "新北市板橋區文化路一段421巷2號1樓",lat: 25.022788, lng: 121.467419},
{name: "板橋埔墘餐廳", address: "新北市板橋區中山路二段249號",lat: 25.017584, lng: 121.477325},
{name: "泰山明志餐廳", address: "新北市泰山區明志路一段326號",lat: 25.059106, lng: 121.43131},
{name: "淡水中山二餐廳", address: "新北市淡水區中山路24號-26號",lat: 25.169636, lng: 121.44444},
{name: "淡水中山餐廳", address: "新北市淡水區中山北路一段151號",lat: 25.177487, lng: 121.443088},
{name: "淡水竹圍餐廳", address: "新北市淡水區民權路41號",lat: 25.140446, lng: 121.460126},
{name: "新店北新餐廳", address: "新北市新店區北新路二段61號",lat: 24.973354, lng: 121.542743},
{name: "新店光明餐廳", address: "新北市新店區光明街1之1號1-5樓",lat: 24.959113, lng: 121.538193},
{name: "新店安康餐廳", address: "新北市新店區安康路二段60號",lat: 24.963776, lng: 121.514869},
{name: "新莊輔大餐廳", address: "新北市新莊區中正路516-3號",lat: 25.03246, lng: 121.433307},
{name: "新莊幸福餐廳", address: "新北市新莊區幸福路763-6號",lat: 25.048872, lng: 121.447871},
{name: "新莊中港餐廳", address: "新北市新莊區中港路311-313號",lat: 25.044636, lng: 121.452435},
{name: "樹林中山餐廳", address: "新北市樹林區中山路一段67號",lat: 24.991854, lng: 121.423791},
{name: "蘆洲長榮餐廳", address: "新北市蘆洲區長榮路80號",lat: 25.083511, lng: 121.462608},
{name: "新北鶯歌餐廳", address: "新北市鶯歌區建國路153號",lat: 24.9535, lng: 121.350502},
{name: "桃園介壽餐廳", address: "桃園市八德區介壽路一段808號",lat: 24.962356, lng: 121.299781},
{name: "桃園大園餐廳", address: "桃園市大園區中正東路142號",lat: 25.062274, lng: 121.201382},
{name: "桃園大溪餐廳", address: "桃園市大溪區員林路一段199號",lat: 24.896555, lng: 121.278814},
{name: "中壢環中東餐廳", address: "桃園市中壢區環中東路249號",lat: 24.963212, lng: 121.25704},
{name: "中壢民族餐廳", address: "桃園市中壢區民族路二段138號",lat: 24.957582, lng: 121.204703},
{name: "中壢中和餐廳", address: "桃園市中壢區中和路106號",lat: 24.954375, lng: 121.225574},
{name: "中壢中正餐廳", address: "桃園市中壢區中正路161號",lat: 24.955317, lng: 121.22148},
{name: "中壢環中東二餐廳", address: "桃園市中壢區環中東路二段150-154號1樓",lat: 24.949585, lng: 121.237102},
{name: "桃園南平餐廳", address: "桃園市桃園區南平路330號1樓",lat: 25.018314, lng: 121.298272},
{name: "桃園中山二餐廳", address: "桃園市桃園區中山路444-446號",lat: 24.992107, lng: 121.302253},
{name: "桃園桃鶯餐廳", address: "桃園市桃園區桃鶯路265號",lat: 24.98355, lng: 121.318798},
{name: "桃園大興西餐廳", address: "桃園市桃園區大興西路二段159號",lat: 25.009963, lng: 121.296452},
{name: "桃園新屋餐廳", address: "桃園市新屋區中山路240號",lat: 24.972635, lng: 121.107985},
{name: "楊梅中山餐廳", address: "桃園市楊梅區中山北路二段6-1號",lat: 24.909087, lng: 121.167057},
{name: "楊梅大成餐廳", address: "桃園市楊梅區大成路107號",lat: 24.910453, lng: 121.145572},
{name: "楊梅北龍餐廳", address: "桃園市龍潭區北龍路309、309-1號",lat: 24.868621, lng: 121.21825},
{name: "林口復興餐廳", address: "桃園市龜山區復興一路110-112號",lat: 25.058963, lng: 121.367698},
{name: "桃園萬壽餐廳", address: "桃園市龜山區萬壽路二段1185號",lat: 24.993337, lng: 121.332038},
{name: "桃園南崁中正餐廳", address: "桃園市蘆竹區南崁村中正路8~12號",lat: 25.043949, lng: 121.294232},
{name: "桃園台茂三餐廳", address: "桃園市蘆竹區南崁路一段112號B2",lat: 25.053301, lng: 121.288132},
{ name: "KFC 桃園大竹店", address: "桃園市蘆竹區大竹路428-1號", lat: 25.020806, lng: 121.264586 },
{ name: "新竹中正店", address: "新竹市東區中正路36號",lat: 24.803583, lng: 120.971313},
{ name: "新竹清大店", address: "新竹市東區光復路二段334號",lat: 24.795648, lng: 120.998045},
{ name: "新竹光復店", address: "新竹市東區光復路一段536號",lat: 24.786789, lng: 121.010865},
{ name: "新竹亞太店", address: "新竹市東區忠孝路300號",lat: 24.80448, lng: 120.984641},
{ name: "新竹民生店", address: "新竹市東區民生路306號",lat: 24.813442, lng: 120.97464},
{ name: "苗栗竹南店", address: "苗栗縣竹南鎮博愛街320-1號",lat: 24.68782, lng: 120.870686},
{ name: "苗栗中正店", address: "苗栗縣苗栗市中正路880號&光復路1號",lat: 24.55206, lng: 120.816079},
{ name: "苗栗頭份店", address: "苗栗縣頭份市中華路1243、1245號1、2樓",lat: 24.690861, lng: 120.913037},
{ name: "竹北中華店", address: "新竹縣竹北市中華路363號",lat: 24.837297, lng: 121.004883},
{ name: "新竹竹北店", address: "新竹縣竹北市光明六路69號",lat: 24.826122, lng: 121.010685},
{ name: "竹北自強店", address: "新竹縣竹北市自強南路95、97號",lat: 24.816467, lng: 121.025806},
{ name: "新竹竹東店", address: "新竹縣竹東鎮長春路三段130號",lat: 24.742864, lng: 121.084352},
{ name: "新竹湖口店(休息站)", address: "新竹縣湖口鄉榮光路158號",lat: 24.860911, lng: 121.012186},
{ name: "新竹新豐店", address: "新竹縣新豐鄉新興路163之5號",lat: 24.867941, lng: 120.993659},
{ name: "台中大甲店", address: "台中市大甲區經國路500號1樓",lat: 24.350166, lng: 120.61737},
{ name: "台中大里國光店", address: "台中市大里區國光路二段75號1-3樓",lat: 24.1005, lng: 120.682181},
{ name: "台中大里中興二店", address: "台中市大里區中興路二段478號",lat: 24.110252, lng: 120.690054},
{ name: "台中大雅店", address: "台中市大雅區中清路三段1215號",lat: 24.225275, lng: 120.645203},
{ name: "台中站前店", address: "台中市中區台灣大道一段38號",lat: 24.138353, lng: 120.684354},
{ name: "台中太平中山店", address: "台中市太平區中山路四段26-6號",lat: 24.149979, lng: 120.712566},
{ name: "台中文心店", address: "台中市北屯區文心路四段153號",lat: 24.17374, lng: 120.676397},
{ name: "台中大買家店", address: "台中市北屯區北屯路370號1F",lat: 24.176387, lng: 120.699798},
{ name: "台中文心二店", address: "台中市北屯區文心路四段520號",lat: 24.172043, lng: 120.684338},
{ name: "台中崇德店", address: "台中市北屯區崇德路三段463號",lat: 24.191184, lng: 120.686197},
{ name: "台中中清店", address: "台中市北屯區中清路二段915-917號",lat: 24.187969, lng: 120.662527},
{ name: "台中五權店", address: "台中市北區五權路202號",lat: 24.150263, lng: 120.67654},
{ name: "台中文心三餐廳", address: "台中市西屯區文心路3段119-1號",lat: 24.168835, lng: 120.655034},
{ name: "台中福星餐廳", address: "台中市西屯區福星北路1號1~2樓",lat: 24.18246, lng: 120.644752},
{ name: "台中河南餐廳", address: "台中市西屯區河南路二段363-7、363-8號",lat: 24.171503, lng: 120.644649},
{ name: "台中西屯家樂福餐廳", address: "台中市西屯區台灣大道四段1086號",lat: 24.184133, lng: 120.614969},
{ name: "台中永福餐廳", address: "台中市西屯區永福路120號",lat: 24.184477, lng: 120.621135},
{ name: "台中公益餐廳", address: "台中市西區公益163號",lat: 24.150691, lng: 120.66141},
{ name: "台中沙鹿餐廳", address: "台中市沙鹿區沙田路127號",lat: 24.235891, lng: 120.55893},
{ name: "台中後車站餐廳", address: "台中市東區復興路四段108號",lat: 24.135431, lng: 120.685643},
{ name: "台中五權西餐廳", address: "台中市南屯區五權西路二段136號",lat: 24.140342, lng: 120.651568},
{ name: "台中五權西二餐廳", address: "台中市南屯區五權西路二段931、933號",lat: 24.144689, lng: 120.63316},
{ name: "台中復興餐廳", address: "台中市南區復興路二段8之1號",lat: 24.119574, lng: 120.660208},
{ name: "台中烏日餐廳", address: "台中市烏日區九德村中山路一段450號",lat: 24.107408, lng: 120.636313},
{ name: "台中清水餐廳", address: "台中市清水區中華路445-1號1樓",lat: 24.273735, lng: 120.56916},
{ name: "台中潭子餐廳", address: "台中市潭子區勝利路311號",lat:  24.213872, lng: 120.700449},
{ name: "台中東海餐廳", address: "台中市龍井區遊園南路185號1-2樓",lat:24.181974, lng:120.584242},
{ name: "台中豐原餐廳", address: "台中市豐原區中正路46號",lat:24.253027, lng:120.721673},
{ name: "台中霧峰餐廳", address: "台中市霧峰區林森路342號",lat:24.055946, lng:120.696202},
{ name: "彰化北斗餐廳", address: "彰化縣北斗鎮中山路二段40號",lat:23.874264, lng:120.518152},
{ name: "彰化和美餐廳", address: "彰化縣和美鎮彰美路四段438號",lat:24.105733, lng:120.5056},
{ name: "彰化花壇餐廳", address: "彰化縣花壇鄉花壇村中山路一段385號",lat:24.023939, lng:120.540999},
{ name: "彰化員林餐廳", address: "彰化縣員林市民權街26、28號",lat:23.959568, lng:120.571139},
{ name: "彰化鹿港餐廳", address: "彰化縣鹿港鎮民權路243號",lat:24.055711, lng:120.433527},
{ name: "彰化金馬餐廳", address: "彰化縣彰化市金馬路二段425號",lat:24.09372, lng:120.540591},
{ name: "彰化中山餐廳", address: "彰化縣彰化市中山路二段126號",lat:24.068044, lng:120.540355},
{ name: "南投竹山餐廳", address: "南投縣竹山鎮大明路716號",lat:23.755001, lng:120.677427},
{ name: "南投南崗餐廳", address: "南投縣南投市南崗二路212號",lat:23.9116221, lng:120.678875},
{ name: "南投埔里餐廳", address: "南投縣埔里鎮中正路340號",lat:23.963888, lng:120.96896},
{ name: "南投草屯餐廳", address: "南投縣草屯鎮太平路2段235號",lat:23.975113, lng:120.683512},
{ name: "斗六民生二餐廳", address: "雲林縣斗六市民生路106號",lat:23.710373, lng:120.540049},
{ name: "斗六民生餐廳", address: "雲林縣斗六市民生南路169號",lat:23.70133, lng:120.537161},
{ name: "雲林北港餐廳", address: "雲林縣北港鎮華南路36號",lat:23.57188, lng:120.30021},
{ name: "雲林虎尾餐廳", address: "雲林縣虎尾鎮中正路30號B1-4樓",lat:23.70964, lng:120.436506},
{ name: "嘉義中興餐廳", address: "嘉義市西區中興路396號",lat:23.479656, lng:120.429127},
{ name: "嘉義垂楊餐廳", address: "嘉義市西區垂楊路460號",lat:23.473862, lng:120.448346},
{ name: "嘉義忠孝餐廳", address: "嘉義市東區忠孝路489號",lat:23.495117, lng:120.451643},
{ name: "嘉義民雄餐廳", address: "嘉義縣民雄鄉建國路一段239號",lat:23.55951, lng:120.43376},
{ name: "嘉義朴子餐廳", address: "嘉義縣朴子市山通路210、212號1樓",lat:23.464031, lng:120.241591},
{ name: "台南民族餐廳", address: "台南市中西區民族路二段64號",lat:22.994583, lng:120.210123},
{ name: "台南中華西餐廳", address: "台南市中西區中華西路二段38號",lat:22.993059, lng:120.187028},
{ name: "台南西門二餐廳", address: "台南市北區西門路四段155號",lat:23.009871, lng:120.207876},
{ name: "台南永康餐廳", address: "台南市永康區中正北路126號",lat:23.041274, lng:120.242506},
{ name: "台南中華餐廳", address: "台南市永康區中華路145號",lat:23.003042, lng:120.234971},
{ name: "台南大灣餐廳", address: "台南市永康區大灣路843、845號",lat:22.999472, lng:120.255835},
{ name: "台南海佃餐廳", address: "台南市安南區海佃路一段138、140號",lat:23.023659, lng:120.191894},
{ name: "台南佳里餐廳", address: "台南市佳里區光復路319號1F",lat:23.162961, lng:120.174555},
{ name: "台南東門餐廳", address: "台南市東區東門路二段369號",lat:22.98241, lng:120.228043},
{ name: "台南崇明餐廳", address: "台南市東區崇明路376號",lat:22.976264, lng:120.22099},
{ name: "台南成大餐廳", address: "台南市東區大學路20號",lat:22.995903, lng:120.220931},
{ name: "台南金華餐廳", address: "台南市南區金華路二段238號",lat:22.981079, lng:120.191605},
{ name: "台南善化餐廳", address: "台南市善化區光復路51號",lat:23.128595, lng:120.292954},
{ name: "台南新營復興餐廳", address: "台南市新營區復興路636號",lat:23.30607, lng:120.295926},
{ name: "台南新營中正餐廳", address: "台南市新營區中正路6-1號",lat:23.307341, lng:120.316269},
{ name: "高雄站前餐廳", address: "高雄市三民區建國三路23號",lat:22.638021, lng:120.301212},
{ name: "高雄大順二餐廳", address: "高雄市三民區大順二路246號",lat:22.644406, lng:120.326385},
{ name: "高雄十全餐廳", address: "高雄市三民區十全一路161號1樓",lat:22.644835, lng:120.309439},
{ name: "高雄鼎中餐廳", address: "高雄市三民區鼎中路400號",lat:22.666911, lng:120.319924},
{ name: "高雄沿海餐廳", address: "高雄市小港區沿海一路145號",lat:22.567477, lng:120.351763},
{ name: "高雄仁武餐廳", address: "高雄市仁武區仁雄路86-43號",lat:22.67726, lng:120.344519},
{ name: "高雄自由餐廳", address: "高雄市左營區自由二路173號",lat:22.660584, lng:120.308616},
{ name: "高雄崇德餐廳", address: "高雄市左營區崇德路311號",lat:22.676238, lng:120.302725},
{ name: "高雄岡山餐廳", address: "高雄市岡山區岡山路271號1-3樓",lat:22.793958, lng:120.29569},
{ name: "高雄瑞隆餐廳", address: "高雄市前鎮區瑞隆路445號",lat:22.605207, lng:120.328979},
{ name: "高雄中華五餐廳", address: "高雄市前鎮區中華五路1111號",lat:22.605079, lng:120.304449},
{ name: "高雄文化餐廳", address: "高雄市苓雅區五福一路22號",lat:22.628061, lng:120.315381},
{ name: "高雄三多餐廳", address: "高雄市苓雅區三多二路193號",lat:22.619684, lng:120.322589},
{ name: "高雄楠梓二餐廳", address: "高雄市楠梓區建楠路150-152號",lat:22.727106, lng:120.326847},
{ name: "高雄後昌餐廳", address: "高雄市楠梓區後昌新路20號",lat:22.718551, lng:120.29215},
{ name: "高雄青海餐廳", address: "高雄市鼓山區青海路151號",lat:22.651162, lng:120.288173},
{ name: "高雄鳳山中山餐廳", address: "高雄市鳳山區中山路215號",lat:22.624496, lng:120.356939},
{ name: "高雄鳳山五甲一餐廳", address: "高雄市鳳山區五甲三路211號&五福二路1號",lat:22.590081, lng:120.324478},
{ name: "高雄鳳山青年餐廳", address: "高雄市鳳山區青年路二段313號",lat:22.640151, lng:120.350893},
{ name: "屏東東港餐廳", address: "屏東縣東港鎮中山路147,149號",lat:22.466589, lng:120.45052},
{ name: "屏東自由餐廳", address: "屏東縣屏東市自由路496號",lat:22.683619, lng:120.489998},
{ name: "屏東逢甲餐廳", address: "屏東縣屏東市逢甲路72號1-3樓",lat:22.670665, lng:120.4870658},
{ name: "屏東潮州餐廳", address: "屏東縣潮州鎮信義路111號",lat:22.550009, lng:120.536108},
{ name: "宜蘭舊城餐廳", address: "宜蘭縣宜蘭市舊城南路2之3號",lat:24.754687, lng:121.754196},
{ name: "宜蘭羅東興東餐廳", address: "宜蘭縣羅東鎮興東路90號1-2樓",lat:24.679556, lng:121.769059},
{ name: "宜蘭礁溪餐廳", address: "宜蘭縣礁溪鄉礁溪路五段67號1樓",lat:24.827458, lng:121.772013},
{ name: "宜蘭蘇澳餐廳", address: "宜蘭縣蘇澳鎮中山路一段90號",lat:24.596284, lng:121.850768},
{ name: "花蓮吉安餐廳", address: "花蓮縣吉安鄉中華路二段1號",lat:23.973958, lng:121.587857},
{ name: "花蓮中正餐廳", address: "花蓮縣花蓮市中正路637-1號",lat:23.979738, lng:121.610196},
{ name: "台東新生餐廳", address: "台東縣台東市新生路158號",lat:22.753169, lng:121.147784},
    ];

    const getDistance = (lat1, lng1, lat2, lng2) => {
      const toRad = (value) => (value * Math.PI) / 180;
      const R = 6371;
      const dLat = toRad(lat2 - lat1);
      const dLng = toRad(lng2 - lng1);
      const a =
        Math.sin(dLat / 2) ** 2 +
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
        Math.sin(dLng / 2) ** 2;
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      return R * c;
    };

    const getUserLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            userLocation.value = {
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            };
            findNearestKfc();
          },
          (error) => {
            console.error("無法獲取位置：", error);
            if (error.code === error.PERMISSION_DENIED) {
              alert("請啟用定位權限以獲取最近的肯德基位置。");
            }
          }
        );
      } else {
        console.error("瀏覽器不支援定位");
      }
    };

    const findNearestKfc = () => {
      if (!userLocation.value) return;
      let minDistance = Infinity;
      let closestStore = null;

      kfcStores.forEach((store) => {
        const distance = getDistance(
          userLocation.value.lat,
          userLocation.value.lng,
          store.lat,
          store.lng
        );
        if (distance < minDistance) {
          minDistance = distance;
          closestStore = store;
        }
      });

      nearestKfc.value = closestStore;
    };

    const openGoogleMaps = () => {
      if (nearestKfc.value) {
        const { name, address } = nearestKfc.value;
        const query = encodeURIComponent(`${name} ${address}`);
        window.open(`https://www.google.com/maps/search/?api=1&query=${query}`, "_blank");
      }
    };

    onMounted(getUserLocation);

    return { nearestKfc, openGoogleMaps };
  },
};
</script>

<style scoped>
@media (max-width: 768px) {
  .coupon-map-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 10px;
    background: white;
    border: 1px solid #ccc;
    border-radius: 10px;
    margin-bottom: 10px;
    background-color: black; /* 測試用，確認 media query 是否生效 */
  }
}



.coupon-map-container {
  position: absolute;
  top: 5px;
  min-width: 300px;
  max-height: 95px;
  background: white;
  padding: 3px;
  border: 2px solid rgb(172, 172, 172);
  border-radius: 8px;
  z-index: 1000;
  margin: auto;
}

.header {
  display: flex;
  justify-content: start;
  align-items: center;
}

.title {
  font-size: 22px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  color: #E4002B;
}

.map-button {
  background-color: red;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  margin-left: 5px;
}

.map-button:hover {
  background-color: #cc0000;
}

.map-button:active {
  background-color: #f3a3a3;
}

.store-info {
  font-size: 15px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  color: #E4002B;
}

.details p {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
}

.action-button {
  background-color: #E4002B;
  color: white;
  font-size: 20px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  padding: 18px 36px;
  border-radius: 10px;
  text-decoration: none;
  text-align: center;
  display: inline-block;
  justify-content: space-between;
  transition: background 0.2s ease-in-out;
  position: absolute;
  right: -200px;
  top: -3px;
}

.action-button:hover {
  background-color: #cc0000;
}

.action-button:active {
  background-color: #f3a3a3;
}
</style>
