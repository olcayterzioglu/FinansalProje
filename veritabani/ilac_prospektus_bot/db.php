<?php
final class db {
	private $connection;
	public $ave = 0;

	public function __construct($hostname, $username, $password, $database) { // veritabanı sunucusuna bağlanma fonksiyonu
		if (!$this->connection = mysqli_connect($hostname, $username, $password,$database)) {
      		exit('Failed to connect to MySQL: ' . mysqli_connect_error());
    	}

		mysqli_query($this->connection, "SET NAMES 'utf8'");	// veritanına veri girerken yada okurken o verinin karakter türünü belirleme
		mysqli_query($this->connection, "SET CHARACTER SET utf8");
		mysqli_query($this->connection, "SET CHARACTER_SET_CONNECTION=utf8");
		mysqli_query($this->connection, "SET SQL_MODE = ''");
  	}


  	public function query($sql) {

		#$bench = array(); $bench['sql'] = $sql; $bench['start'] = (time() + microtime());

		$resource = @mysqli_query($this->connection, $sql);

		#$bench['end'] = (time() + microtime()); $bench['ave'] =  round((time() + microtime()) - $bench['start'], 4); print '<pre>'; print_r($bench); print '</pre>';

		#$this->ave = $this->ave + $bench['ave'];


		if ($resource) {

			if ( $resource instanceof mysqli_result ) {
				$i = 0;

				$data = array();

				while ($result = mysqli_fetch_assoc($resource)) {
					$data[$i] = $result;
					$i++;
				}

				mysqli_free_result($resource);

				$query = new stdClass();
				$query->row = isset($data[0]) ? $data[0] : array();
				$query->rows = $data;
				$query->num_rows = $i;

				unset($data);

				return $query;
    		} else {
				return TRUE;
			}
		} else {
			exit('Error: ' . mysqli_error($this->connection) . '<br />Error No: ' . mysqli_errno($this->connection) . '<br />' . $sql);
    	}
  	}  

	public function escape($value) { // özel tırnakları kapat.
		return mysqli_real_escape_string($this->connection, $this->escapeSpecialChr($this->escapeChar($value))); // sql injection ayikla
	}

	public function escapeSpecialChr($item) {
		$oldArray = array('‘', '’', '“', '”');
		$newArray = array( "'" , "'" , '"', '"');
		return str_replace($oldArray, $newArray, $item);
	}

	public function escapeText($item) {
		if(is_array($item)){
			foreach($item as $key => $value){
				if(is_array($value)){
					$item[$key] = $this->escapeText($value);
				}else{
					$item[$key] = htmlspecialchars($value,ENT_COMPAT);
				}
			}
		}else{
			$item 	= 	htmlspecialchars($item,ENT_COMPAT); //ENT_QUOTES
		}
		return $item;
 	}

	/**
		* Karakter hatalarını düzenleme
		* @param $value     =   Metin
		* @param $escType   =   Tırnak çevirme kontrolü  ( yalnızca çift tırnak = 'DQ', yalnızca tek tırnak = 'SQ', Her ikisi = 'Q', tırnak çevirme = false)
		* @return false | string
		*
		* Db kayıt esnasında $escType parametresini false olarak kullan. (Yalnızca ekranda listelerken gözükmesi içindir.)
		*/
	public function escapeChar($value, $escType = false){

		if(!$value || empty($value) || !is_string($value)) return false;
		$value 		=		html_entity_decode($value, ENT_NOQUOTES);
		$escType 	=		( $escType && in_array($escType, array("DQ", "SQ", "Q")) ?  $escType : false );

		$characters = array(
			'ğ'	=> array('&gbreve;', '&#x0011F;', '&#287;'),
			'Ğ'	=> array('&Gbreve;', '&#x0011E;', '&#286;'),
			'ı'	=> array('&inodot;', '&#x00131;', '&#305;', '&imath;'),
			'İ'	=> array('&Idot;', '&#x00130;', '&#304;'),
			'ü'	=> array('&uuml;', '&#x000FC;', '&#252;'),
			'Ü'	=> array('&Uuml;', '&#x000DC;', '&#220;'),
			'ş'	=> array('&scedil;', '&#x0015F;', '&#351;'),
			'Ş'	=> array('&Scedil;', '&#x0015E;', '&#350;'),
			'ö'	=> array('&ouml;', '&#x000F6;', '&#246;'),
			'Ö'	=> array('&Ouml;', '&#x000D6;', '&#214;'),
			'ç'	=> array('&ccedil;', '&#x000E7;', '&#231;'),
			'Ç'	=> array('&Ccedil;', '&#x000C7;', '&#199;'),
			'"'	=> array('&quot;', '&QUOT;', '&#x00022;', '&#34;'),
			' '	=> array('&nbsp;', '&NonBreakingSpace;', '&#x000A0;', '&#160;')
		);

		if($escType){
			if( $escType == "DQ" || $escType == "Q"){
					$value = preg_replace('@"(.*)"@isU','“$1”', $value);
					$characters['“']		=		array('&ldquo;', '&#x0201C;', '&OpenCurlyDoubleQuote;', '&#8220;', '"');
			}

			if( $escType == "SQ"  || $escType == "Q"){
					$characters['\'']		=		array('&apos;', '&#700;', '&#x00027;', '&#39;', '&rsquo;', '&rsquor;', '&CloseCurlyQuote;', '&#x02019;', '&#8217;'); //, "'" ’
			}
		}


		while (list($character, $replacement) = each($characters)) {
				$value = str_replace($replacement, $character, $value);
		}

		return $value;
	}

	public function countAffected() {
  	return mysqli_affected_rows($this->connection); // önceki mysql sorgusundan etkilenen satirlarin sayısını öğren
	}

	public function getLastId() {
  	return mysqli_insert_id($this->connection); // son sorguda autoincrement özelliği ile oluşturulmuş olan ID numarasına erişmek için kullanılır
	}

	public function __destruct() {
		mysqli_close($this->connection); // database kapat
	}
}
?>
