angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const coreContextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.currentPage = 1;
    $scope.pageSize = 7;

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            method: 'GET',
            url: coreContextPath + 'api/v1/products',
            params: {
                title: $scope.productsfilter ? $scope.productsfilter.title : null,
                min_price: $scope.productsfilter ? $scope.productsfilter.maxPrice : null,
                max_price: $scope.productsfilter ? $scope.productsfilter.minPrice : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;

        });
    }

    $scope.showProductInfo = function (productId) {
        $http.get(coreContextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(coreContextPath + 'api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addToCart = function (productId) {
        console.log(productId + ' added to cart')
        $http.get(cartContextPath + 'api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadProducts();

})