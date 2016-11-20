'user strict';
angular.module('product', ['angularFileUpload'])

    .controller('UploadController',
        function ($scope, FileUploader) {

            $scope.uploadError = {
                show: false,
                message: ""
            };

            $scope.uploader = new FileUploader({
                url: "/upload",
                onErrorItem: function (error) {
                    showUploadError(formatError(error._xhr.responseText));
                }
            });

            function showUploadError(message) {
                $scope.uploadError.show = true;
                $scope.uploadError.message = message;
            }

            function formatError(error){
                return error.replace("{\"response\":\"","").replace("\"}","");
            }

        })

    .controller('ProductController',
        ['$scope', '$timeout', 'ProductService', function ($scope, $timeout, productService) {

            $scope.products = [];
            $scope.pagination = {
                currentPage: 0,
                isLastPage: false
            };

            $scope.tableError = {
                show: false,
                message: ""
            };


            $scope.getNextPage = function (page) {
                productService.load(page).then(function (products) {
                    if (products.length == 0) {
                        $scope.pagination.isLastPage = true;
                    } else {
                        $scope.pagination.currentPage = page;
                        $scope.products = products;
                    }
                });
            };

            $scope.getPreviousPage = function (page) {
                productService.load(page).then(function (products) {
                    if ($scope.pagination.currentPage != 0) {
                        $scope.products = products;
                        $scope.pagination.currentPage = page;
                        $scope.pagination.isLastPage = false;
                    }
                });
            };

            $scope.getNextPage(0);

            function showTableError(message) {
                $scope.tableError.show = true;
                $scope.tableError.message = message;
                $timeout(function () {
                    $scope.tableError.show = false;
                }, 3000);
            }

        }])

    .factory('ProductService', function ($http) {

        function load(page) {
            console.log("requesting page " + page);
            return $http.get('/product-table/?page=' + page).then(
                function (success) {
                    console.log(success);
                    return success.data.response;
                }, function (error) {
                    console.log(error);
                    return [];
                });
        }

        return {
            load: load
        }

    })


    .directive('productTable', function () {
        return {
            restrict: "E",
            templateUrl: 'static/javascript/products/product-table.template.html',
            controller: 'ProductController'
        }
    })

    .directive("fileForm", function () {
        return {
            restrict: "E",
            templateUrl: 'static/javascript/products/file-form.template.html',
            controller: 'UploadController'
        }
    });
