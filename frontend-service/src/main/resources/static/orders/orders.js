angular.module('market').controller('ordersController', function ($scope, $http) {
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(coreContextPath + 'api/v1/orders').then(function (response) {
            $scope.orders = response.data;
            console.log($scope.orders)
        });
    }

    $scope.loadOrders();
})