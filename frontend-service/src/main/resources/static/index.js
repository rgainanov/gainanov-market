angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post("http://localhost:5555/auth/auth", $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.gainanovMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                }
            }, function errorCallback(response) {
            });
    }

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    }

    $scope.clearUser = function () {
        delete $localStorage.gainanovMarketUser;
        $http.defaults.headers.common.Authorization = '';
    }

    $scope.isUserLoggedIn = function () {
        if ($localStorage.gainanovMarketUser) {
            return true;
        } else {
            return false;
        }
    }

    $scope.authCheck = function () {
        $http.get('http://localhost:5555/core/auth_check')
            .then(function (response) {
                alert(response.data.value)
            });
    }

    $scope.createOrder = function () {
        $http.post('http://localhost:5555/core/api/v1/orders', $scope.order)
            .then(function (response) {
                $scope.getUserOrders();
                $scope.loadCart();
            });

    }

    $scope.getUserOrders = function () {
        $http.get('http://localhost:5555/core/api/v1/orders/')
            .then(function (response) {
                $scope.userOrders = response.data
            });
    }

    if ($localStorage.gainanovMarketUser) {
        try {
            let jwt = $localStorage.gainanovMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is Expired");
                delete $localStorage.gainanovMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {

        }
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.gainanovMarketUser.token;

    }

    $scope.loadProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products').then(function (response) {
            $scope.productsList = response.data;
        });
    }

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:5555/core/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
            console.log('test')
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete('http://localhost:5555/core/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/decrease/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeProductLine = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/remove-line/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.loadProducts();
    $scope.loadCart();

});