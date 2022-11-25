// eslint-disable-next-line no-undef
module.exports = {
	"env": {
		"browser": true,
		"es2020": true
	},

	"extends": [
		"eslint:recommended",
		"plugin:@typescript-eslint/recommended"
	],

	"parser": "@typescript-eslint/parser",

	"parserOptions": {
		"ecmaVersion": 12,
		"sourceType": "module"
	},

	"plugins": [
		"@typescript-eslint"
	],

	"rules": {
		"indent": [
			"error",
			"tab"
		],

		"linebreak-style": [
			"error",
			"windows"
		],

		"quotes": [
			"error",
			"double"
		],

		"@typescript-eslint/no-namespace": "off",
		"@typescript-eslint/prefer-namespace-keyword": "off",

		"@typescript-eslint/no-non-null-assertion": "off",

		"@typescript-eslint/no-explicit-any": "off",

		"@typescript-eslint/no-empty-function": "warn",
	}
};
