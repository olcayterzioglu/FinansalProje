<?php

mb_internal_encoding('UTF-8');
 
function _print($value)
{
	echo "<pre>";
	print_r($value);
	echo "</pre>";
}

$search_page = 0;
$pages = 0;
$search_key = 0;

if ($_SERVER['REQUEST_METHOD'] === 'POST'){
	$search_key = $_POST['key']; 
}


$keyList = array();

$keyList[] = array('key' => 'a', 'value' => 'A');
$keyList[] = array('key' => 'b', 'value' => 'B');
$keyList[] = array('key' => 'c', 'value' => 'C');
$keyList[] = array('key' => 'd', 'value' => 'D');
$keyList[] = array('key' => 'e', 'value' => 'E');
$keyList[] = array('key' => 'f', 'value' => 'F');
$keyList[] = array('key' => 'g', 'value' => 'G');
$keyList[] = array('key' => 'h', 'value' => 'H');
$keyList[] = array('key' => 'i', 'value' => 'I');
$keyList[] = array('key' => 'j', 'value' => 'J');
$keyList[] = array('key' => 'k', 'value' => 'K');
$keyList[] = array('key' => 'l', 'value' => 'L');
$keyList[] = array('key' => 'm', 'value' => 'M');
$keyList[] = array('key' => 'n', 'value' => 'N');
$keyList[] = array('key' => 'o', 'value' => 'O');
$keyList[] = array('key' => 'p', 'value' => 'P');
$keyList[] = array('key' => 'q', 'value' => 'Q');
$keyList[] = array('key' => 'r', 'value' => 'R');
$keyList[] = array('key' => 's', 'value' => 'S');
$keyList[] = array('key' => 't', 'value' => 'T');
$keyList[] = array('key' => 'u', 'value' => 'U');
$keyList[] = array('key' => 'v', 'value' => 'V');
$keyList[] = array('key' => 'w', 'value' => 'W');
$keyList[] = array('key' => 'x', 'value' => 'X');
$keyList[] = array('key' => 'y', 'value' => 'Y');
$keyList[] = array('key' => 'z', 'value' => 'Z');

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title of the document</title>
    <script src="jquery-3.1.0.min.js"></script>

</head>
<body>

<div style="width: 700px; margin:10% auto 0 auto; padding: 10px; text-align: center; border:1px solid #333;">
    <h3>İlaç Prospektüsü Listeleme Botu</h3>
    <form method="post">
        <select name="key" id="searchkey" style="width: 100px;">
			<?php
			foreach ($keyList as $value){
				echo '<option ' . ($search_key == $value['key'] ? 'selected ' : '') . 'value="' . $value['key'] . '">' . $value['value'] . '</option>';
			}
			?>
        </select> 

        <br><br>
        <button type="submit">Listele</button>
        <br><br>
    </form>
</div>

<?php

if ($search_key){

	$alfabe = false;
	$liste = false;
	$sayfa = false;

	$url = 'http://www.ilacprospektusu.com/ilac/rehber/' . mb_strtolower($search_key, 'UTF-8'); 
	$result = getContents($url);


	preg_match_all('/<div class=\"liste\">(.*?)<\/div>/s', $result, $liste_list);
	if (count($liste_list[1])){
		$liste = $liste_list[1][0];
		$liste = str_replace(array('<ul>', '</ul>', '<li>', '</li>'), '', $liste);
		$liste = str_replace('//www', 'https://www', $liste);
	}

	if (preg_match_all('/<a\s+href=["\']([^"\']+)["\']/i', $liste, $links, PREG_PATTERN_ORDER)){
		$all_hrefs = array_unique($links[1]);
	}

	//_print($all_hrefs);

	//getDetails(array('https://www.ilacprospektusu.com/ilac/88/a-ferin-ped-100-ml-surup'));
	getDetails($all_hrefs);
}


function getDetails($links)
{  

	foreach ($links as $link){
		$url = $link;
		//$result = getContents('https://www.ilacprospektusu.com/ilac/14/a-nox-fort-550-mg-20-tablet');
		//echo "<br>" . $url . "<br>";
		$result = getContents($url);


		$prospektus = "";
		$ilac_bilgisi = "";
		$firma_bilgisi = "";

		preg_match_all('/<div class=\"prospektus\" id=\"prospektus\">(.*?)<div class=\"ilacesdegerleri\" id=\"ilacesdegerleri\">/s', $result, $prospektus); 

		$ilac_bilgisi = $ilac[1][0]; 


		$prospektus = getDetailIlacProspektus($prospektus, 0);  
 
		//_print($prospektus);

		echo $url."<br>";
		 
	} 
}


function getDetailIlacProspektus($data, $key)
{ 
	$data = strip_tags($data, '<h3><strong><br><div>');
	$data = str_replace(array('<strong>', '</strong>'), array('<h3>', '</h3>'), $data);
	$data = preg_replace("/<\/?(div|span)[^>]*\>/i", "", $data);
	$data = preg_replace("/<div class=\"kubktliste\">(.*?)<\/ul>/i", "", $data);
	$data = str_replace('(adsbygoogle = window.adsbygoogle || []).push({});', '', $data);

	preg_match_all('/<h3>(.*?)<\/h3>/s', $data, $basliklar);
	//_print($basliklar[1]); 
	
	$icerikler = "";
	$data .= '<h3></h3>';
	preg_match_all('/<\/h3>(.*?)<h3>/s', $data, $icerikler);
	//_print($icerikler[1]);

	$ddd = array_combine($basliklar[1], $icerikler[1]);

	return $ddd;
}
  


function getContents($url)
{
	$result = file_get_contents($url); 
	return $result;
} 
?>  
</body>
</html>