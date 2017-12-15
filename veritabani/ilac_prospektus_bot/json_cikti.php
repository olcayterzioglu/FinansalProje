<?php
 
	require_once 'db.php';
	
	function fixTRCharacter($content)
	{
		$bad_chr = array('Ã¼','Ãœ','Ã§','Åž','ÅŸ','Ä±','ÄŸ','Ã¶','Ã–');
		$new_chr = array('ü','Ü','ç','Ş','ş','ı','ğ','ö','Ö');

		return str_replace($bad_chr, $new_chr, $content);
	}
	
	function fixTRCharacter2($content)
	{
		$bad_chr = array('\n','<BR>');
		$new_chr = array(' ',' ');

		return str_replace($bad_chr, $new_chr, $content);
	}
 
	
	function fixTRUnicode($content)
	{
		/*$bad_chr = array('\u011f','\u011e','\u0131','\u0130','\u00f6','\u00d6','\u00fc','\u00dc','\u015f','\u015e','\u00e7','\u00c7','\u00a0', '\\\n');
		$new_chr = array('ğ','Ğ','ı','İ','ö','Ö','ü','Ü','ş','Ş','ç','Ç',' ',' '); */
		
		$bad_chr = array('.','\u00a0','\/');
		$new_chr = array('\u2024',' ','\u2215'); 
		return str_replace($bad_chr, $new_chr, $content);
	} 
	
	function _print($value)
	{
		echo "<pre>";
		print_r($value);
		echo "</pre>";
	}
	
	
	function String2Hex($string){
		$hex='';
		for ($i=0; $i < strlen($string); $i++){
			$hex .= dechex(ord($string[$i]));
		}
		return $hex;
	}
	 
	 
	function Hex2String($hex){
		$string='';
		for ($i=0; $i < strlen($hex)-1; $i+=2){
			$string .= chr(hexdec($hex[$i].$hex[$i+1]));
		}
		return $string;
	}
	 
	
	
	$db = new db('localhost','root','','ilac_prospektus');
	
	
	$query = $db->query("
		SELECT * FROM ilac_prospektus.ilaclar
		WHERE
		barkod !='' AND
		etken_madde !='' AND
		firma  !='' AND
		formul  !='' AND
		farmakolojik_ozellik !='' AND
		endikasyon !='' AND
		kontrendikasyon !='' AND
		kullanim_sekli !='' AND
		etkilesimler !='' 
	"); 
	//_print($query->row);
 
 
	$data['ilaclar'] = array();
 
	
 
	/*foreach($query->rows as $row){  
		$fiyat = Hex2String(str_replace('c2a0','',String2Hex($row['fiyat']))).' TL';
		$sql = "UPDATE ilaclar SET fiyat = '" . $fiyat . "' WHERE id = '".$row['id']."'";
		//echo $row['id'].' ____ ';
		$db->query($sql); 
	}*/
	
	
	
	foreach($query->rows as $row){
		
		$data['ilaclar'][$row['ilac_ismi']] = array( 
				'ad' 						=>	$row['ilac_ismi'],
				'barkod_no' 				=>	str_replace(' ','',$row['barkod']), 
				'doz_asimi' 				=>	fixTRCharacter2($row['doz_asimi']),
				'endikasyonlar' 			=>	fixTRCharacter2($row['endikasyon']),
				'etken_madde' 				=>	str_replace('+',',',$row['etken_madde']),
				'etkilesimler' 				=>	fixTRCharacter2($row['etkilesimler']),
				'farmokolojik_ozellik' 		=>	fixTRCharacter2($row['farmakolojik_ozellik']),
				'firma_adi' 				=>	$row['firma'], 
				'fiyat' 					=>	str_replace('\u00a0','',$row['fiyat']), 
				'formul' 					=>	fixTRCharacter2($row['formul']),
				'kontrendikasyonlar' 		=>	fixTRCharacter2($row['kontrendikasyon']),
				'kullanim_sekli' 			=>	fixTRCharacter2($row['kullanim_sekli']),
				'uyarilar' 					=>	fixTRCharacter2($row['uyarilar']),
				'yan_etkiler' 				=>	fixTRCharacter2($row['yan_etkiler']),  
			);
	}
	
	$json = fixTRUnicode(json_encode($data)); 
	$fp = fopen("db.json","w");
	fwrite($fp,$json);
	fclose($fp);
	  
 ?>