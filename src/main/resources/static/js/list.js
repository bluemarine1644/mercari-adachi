var bigCategoryList;
var selectedBigCategory;
var middleCategoryList;
var selectedMiddleCategory;
var smallCategoryList;
// 大カテゴリーのプルダウン設定
function createBigCategorySelect() {
	// 文字列を配列に格納
	var bigOptions = '<option value="">カテゴリを選択してください</option>';
	// 選択した大カテゴリIDに対応した商品にだけslected属性を付与
	for (let i = 0; i < bigCategoryList.length; i++) {
		let bigCategory = bigCategoryList[i];
		let selectedStr = $('#displayItemListForm [name=bigCategoryId]').val() == bigCategory.id ? ' selected' : '';
		bigOptions += '<option value="' + bigCategory.id + '"' + selectedStr + '>' + bigCategory.name + '</option>';
	}
	// 文字配列を送る
	$('#bigSelect').html(bigOptions);
	createMiddleCategorySelect();
}
// 中カテゴリのプルダウン生成
function createMiddleCategorySelect() {
	// 選択された親カテゴリIDを持ってくる
	let selectedBigCategoryValue = $('#bigSelect option:selected').val();
	if (selectedBigCategoryValue != '') {
		// 中カテゴリリストに選択された親カテゴリに対応した中カテゴリ情報を格納していく
		for (let i = 0; i < bigCategoryList.length; i++) {
			let bigCategory = bigCategoryList[i];
			if (bigCategory.id == selectedBigCategoryValue) {
				selectedBigCategory = bigCategory;
				// 文字列を配列に格納
				var middleOptions = '<option value="">カテゴリを選択してください</option>';
				middleCategoryList = bigCategory.childCategoryList;
				// 選択された中カテゴリIDに対応した商品にだけselected属性を付与
				for (let j = 0; j < middleCategoryList.length; j++) {
					let middleCategory = middleCategoryList[j];
					let selectedStr = $('#displayItemListForm [name=middleCategoryId]').val() == middleCategory.id ? ' selected' : '';
					middleOptions += '<option value="' + middleCategory.id + '"' + selectedStr + '>' + middleCategory.name + '</option>';
				}
				// 文字配列を送る
				$('#middleSelect').html(middleOptions);
			}
		}
		createSmallCategorySelect();
	} else {
		// カテゴリが選択されていなかった場合は初期値を送る
		$('#middleSelect').html('');
		$('#middleCategoryId').val('');
		$('#smallSelect').html('');
		$('#smallCategoryId').val('');
	}
}
// 小カテゴリのプルダウン生成
function createSmallCategorySelect() {
	let selectedMiddleCategoryValue = $('#middleSelect option:selected').val();
	if (selectedMiddleCategoryValue != '') {
		for (let i = 0; i < middleCategoryList.length; i++) {
			let middleCategory = middleCategoryList[i];
			if (middleCategory.id == selectedMiddleCategoryValue) {
				selectedMiddleCategory = middleCategory;
				var smallOptions = '<option value="">カテゴリを選択してください</option>';
				smallCategoryList = middleCategory.childCategoryList;
				for (let j = 0; j < smallCategoryList.length; j++) {
					let smallCategory = smallCategoryList[j];
					let selectedStr = $('#displayItemListForm [name=smallCategoryId]').val() == smallCategory.id ? ' selected' : '';
					smallOptions += '<option value="' + smallCategory.id + '"' + selectedStr + '>' + smallCategory.name + '</option>';
				}
				$('#smallSelect').html(smallOptions);
			}
		}
	} else {
		$('#smallSelect').html('');
		$('#smallCategoryId').val('');
	}
}
// カテゴリのプルダウンの選択文字を連結
function createCategoryName() {
	let searchCategoryName = '';
	if ($('#bigSelect option:selected') && $('#bigSelect option:selected').val() != '') {
		searchCategoryName += $('#bigCategory option:selected').text();
		if ($('#middleSelect option:selected').val() != '') {
			searchCategoryName += $('#middleSelect option:selected').text();
			if ($('#smallSelect option:selected').val() != '') {
				searchCategoryName += $('#smallSelect option:selected').text();
			}
		}
	}
	return searchCategoryName;
}
$(function() {
	$('#middleSelect').hide();
	$('#smallSelect').hide();

	// 大カテゴリープルダウン変更時のイベント処理設定
	$('#bigSelect').on('change', function() {
		createMiddleCategorySelect();
		$('#middleSelect').show();
		$('#smallSelect').hide();
	});

	// 中カテゴリープルダウン変更時のイベント処理設定
	$('#middleSelect').on('change', function() {
		createSmallCategorySelect();
		$('#smallSelect').show();
	});

	// 検索ボタンクリック時のイベント処理設定
	$('#button-search').on('click', function() {
		$('#displayItemListForm [name=searchCategoryName]').val(createCategoryName());
		$('#displayItemListForm').submit();
	});

	// 全カテゴリ情報取得
	$.getJSON('./categoryList').done(function(jsonCategoryList) {
		bigCategoryList = jsonCategoryList;
		createBigCategorySelect();
	}).fail(function() {
		console.log('失敗');
	}).always(function() {	
	});	
})