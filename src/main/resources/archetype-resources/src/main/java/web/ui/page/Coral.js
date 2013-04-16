var Coral = {};

Coral.focus = function(pId) {
	var tag = document.getElementById(pId);
	if (tag) {
		tag.focus();
	}
};