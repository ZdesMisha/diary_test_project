'use strict';
angular.module('main', ['product','ui.router', 'ui.bootstrap'])
    .config(['$stateProvider', '$urlRouterProvider',
        function config($stateProvider, $urlRouterProvider) {

            $stateProvider
                .state('table', {
                    url: "/table",
                    template: '<product-table></product-table>'
                })

                .state('file-form', {
                    url: "/file-form",
                    template: '<file-form></file-form>'
                });
            $urlRouterProvider.otherwise('/file-form');
        }
    ])
    .controller('ViewController', ['$scope','$state', function ($scope,$state) {

        $scope.tab = $state.current.url;
        $scope.selectPanel = function (url) {
            $scope.tab = url;
        };

        $scope.isSelected = function (panel) {
            return $scope.tab == panel;
        }
    }])
    .directive('viewPanel', function () {
        return {
            restrict: "E",
            templateUrl: 'static/javascript/view-panel.template.html',
            controller: 'ViewController',
            controllerAs: "viewCtrl"
        }
    });