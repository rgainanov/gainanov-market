angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const cartContextPath = 'http://localhost:5555/cart/';
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadCart = function () {
        $http.get(cartContextPath + 'api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.removeFromCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/decrease/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get(cartContextPath + 'api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeProductLine = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/remove-line/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function () {
        $http.post(coreContextPath + 'api/v1/orders', $scope.order)
            .then(function (response) {
                $scope.loadCart();
            });

    }
    $scope.loadCart();
})